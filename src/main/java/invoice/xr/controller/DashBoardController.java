package invoice.xr.controller;


import invoice.xr.model.DashBoard;
import invoice.xr.service.DashBoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashBoardController {
    @Autowired
    DashBoardService dashBoard;

    @GetMapping("/dashBoard")
    public DashBoard createInvoice (){
        DashBoard db= dashBoard.getDashBoard();
        return db;
    }

}
