import { ErrorHandler, Injectable, Injector } from "@angular/core";
import { HttpErrorResponse } from "@angular/common/http";
//import {MessageService} from 'primeng/api';
import { ToastrService } from "ngx-toastr";


@Injectable()
export class GlobalErrorHandler extends ErrorHandler {
  constructor(private injector: Injector) {
    super();
  }

  handleError(error: Error | HttpErrorResponse) {
    const notificationService = this.injector.get(ToastrService);

    console.log("global error handler");
    if (error) {
     // this.messageService.add({severity:'success', summary:'Service Message', detail:'Via MessageService'});
     notificationService.clear();
      notificationService.error(
        "Please report " + error.message
          ? error.message
          : "Unknown error" +
            ' to <a href="mailto:BISC_Helpdesk@niaid.nih.gov">ImmPort Help Desk</a>',
        "Sorry, something went wrong.", { onActivateTick: true }
      );
     // console.log("error");
    }
    //this.messageService.clear();
    //this.messageService.add({severity:'error', summary:'An error occured while performing the request', detail:'Please contact the help desk for reportwriter'})
    super.handleError(error);
  }
}
