package invoice.xr.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import invoice.xr.service.PaymentRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

import invoice.xr.Utils.ApiResponse;
import invoice.xr.Utils.URLUtils;
import invoice.xr.config.PaypalPaymentIntent;
import invoice.xr.config.PaypalPaymentMethod;
import invoice.xr.model.InvoiceModel;
import invoice.xr.service.InvoiceService;
import invoice.xr.service.PaypalService;

/**
 * PaymentController allows the client to do payments.
 * 
 * @author Arshath Mohammed
 *
 */
@Controller
@RequestMapping("/")
public class PaymentController {
	
	public static final String PAYPAL_SUCCESS_URL = "pay/success";
	public static final String PAYPAL_CANCEL_URL = "pay/cancel";
	
	private Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private PaypalService paypalService;
	
	@Autowired
	private InvoiceService invoiceService;

	/**pay is used to update paypal payments 
	 * @param request
	 * @param response
	 * @param total
	 * @param invoiceNo
	 * @return ApiResponse
	 * @throws IOException
	 */
	@GetMapping("pay")
	public ResponseEntity<ApiResponse> pay(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(name = "total") Double total, @RequestParam(name = "invoiceNo") String invoiceNo) throws IOException{
		String cancelUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_CANCEL_URL;
		String successUrl = URLUtils.getBaseURl(request) + "/" + PAYPAL_SUCCESS_URL;
		try {
			Payment payment = paypalService.createPayment(
					total, 
					"GBP", 
					PaypalPaymentMethod.credit_card, 
					PaypalPaymentIntent.order,
					invoiceNo, 
					cancelUrl, 
					successUrl);
			for(Links links : payment.getLinks()){
				if(links.getRel().equals("approval_url")){
					response.sendRedirect(links.getHref());
				}
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		 return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Payment Success"), HttpStatus.CREATED);
	}

	/**cancelPay is used to redirect cancel page on payment cancellation
	 * @return
	 */
	@GetMapping(PAYPAL_CANCEL_URL)
	public ModelAndView cancelPay(){
		return new ModelAndView("redirect:"+"http://localhost:8080/cancel");
	}

	
	/**successPay is used to update payment in database and redirect to success page
	 * @param paymentId
	 * @param payerId
	 * @return
	 * @throws IOException
	 */
	@GetMapping(PAYPAL_SUCCESS_URL)
	public ModelAndView successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) throws IOException{
		try {
			Payment payment = paypalService.executePayment(paymentId, payerId);
			Double paid = Double.parseDouble(payment.getTransactions().get(0).getAmount().getTotal());
			String invoiceNo = payment.getTransactions().get(0).getDescription();
			invoiceService.updateInvoicePayment(paid, invoiceNo);
			if(payment.getState().equals("approved")){
				return new ModelAndView("redirect:"+"http://localhost:8080/success");
			}
		} catch (PayPalRESTException e) {
			log.error(e.getMessage());
		}
		return new ModelAndView("redirect:"+"http://localhost:8080/success");
	}


	/**payDirectly is used to do payment directly
	 * @param invoiceId
	 * @param paid
	 * @return
	 */
	@GetMapping("/payDirectly")
	public ResponseEntity<String> payDirectly(@RequestParam("invoiceId") String invoiceId, @RequestParam("paid") String paid){
		invoiceService.updateInvoicePayment(Double.parseDouble(paid), invoiceId);
		return new ResponseEntity<>("pay successful", HttpStatus.CREATED);
	}
}
