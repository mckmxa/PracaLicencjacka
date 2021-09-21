
import { Component, OnInit } from '@angular/core';
import { Item } from '../models/item.model';
import { History } from '../models/history.model';
import { WishlistService } from '../services/wishlist.service';
import { Alert } from '../models/alert.model';
import { NotificationService } from '../services/notification.service';


@Component({
  selector: 'app-wishlist',
  templateUrl: './wishlist.component.html',
  styleUrls: ['./wishlist.component.css']
})

export class WishListComponent implements OnInit {
  array:{ name: string; value: any; }[]
  
  isChecked = true;
  toggleState: boolean;
  toggle: boolean;
  content?: string;
  idList: String[] = [];
  items: Item[] = [];
  status: any[] = [];
  alerts: Alert[] = [];
  history: History[] = [];
  id = '';
  emptyMessage = 'Your list is empty';

  constructor(private wishListService: WishlistService, private notifyService: NotificationService) {

  }
  ngOnInit(): void {
    this.history = []
    this.retrieveWishList();
    this.retrieveStatus();
    this.retrieveAlertsInfo();
  }


  startTracking(itemId:String,valueAlert: HTMLInputElement ) {
    this.wishListService.startTracking(itemId,valueAlert.value).subscribe(data => {
      console.log(data)
      this.retrieveStatus();
      this.retrieveAlertsInfo();
      this.showToasterSuccessStart();

    }, error => {
      console.log(error)
      this.showToasterErrorStart();
    })
  }

  stopTracking(id:String) {
    this.wishListService.stopTracking(id).subscribe( data => {
      this.retrieveStatus();
      this.showToasterSuccessStop();
    },error => {
      console.log(error)
      this.showToasterErrorStop();
    }
    )
  }


  retrieveStatus(){
    this.wishListService.getStatus().subscribe(data => {
      this.status = data;
      console.log(data)
    })
  }


  retrieveHistory(id:String) {
    this.wishListService.getHistory(id)
    .subscribe(
      data => {
        this.history = data;
        console.log(this.history)
       const input = data[0].datePrice
        this.array = Object.keys(input).map((key) => {
      return {
        name: key,
        value: input[key]
      }
});

        console.log(this.array)
        this.showToasterSuccessHistory();
        
      }, error => {
        console.log(error)
        this.showToasterErrorHistory();
      }
    )
    
  }

  retrieveAlertsInfo() {
    this.wishListService.getAlertsInfo()
      .subscribe(
        data => {
          this.alerts = data;
          console.log(data)
            },
        error => {
          console.log(error);
        });
  }

  retrieveWishList() {
    this.wishListService.getAll()
      .subscribe(
        data => {
          this.items = data;
          for (const val of this.items) {
            val.name = val.name?.slice(0,30) + "..."

           }

          
          console.log(data)
            },
        error => {
          console.log(error);
        });
  }

  isEmpty() {
    if(this.items.length === 0) {
      return true
    } else {
      return false;
    }
  }

  remove(id: String): void {
    this.wishListService.delete(id)
    .subscribe(
      response => {
        console.log(response);
        //this.retrieveWishList();
        this.history = []
        this.showToasterSuccessRemove()
        this.ngOnInit();
      },
      error => {
        console.log(error);
        this.showToasterErrorRemove()
      }
    )
  }
 
  clearList(): void {
    this.wishListService.deleteAll()
      .subscribe(
        response => {
          console.log(response);
          this.retrieveWishList();
          this.history = []
          this.showToasterSuccessClear()
        },
        error => {
          console.log(error);
          this.showToasterErrorClear()
        });

        
  }


  // CUSTOMIZING ALERTS FOR THIS COMPONENT //
showToasterSuccessClear(){
  this.notifyService.showSuccess("Successfully cleared list", "dealalert.com")
}


showToasterSuccessRemove(){
  this.notifyService.showSuccess("Successfully deleted product", "dealalert.com")
}


showToasterSuccessStart(){
  this.notifyService.showSuccess("Alerts successfully enabled", "dealalert.com")
}

showToasterSuccessStop(){
  this.notifyService.showSuccess("Alerts successfully disabled", "dealalert.com")
}

showToasterSuccessHistory(){
  this.notifyService.showSuccess("Price history loaded", "dealalert.com")
}

showToasterErrorClear(){
  this.notifyService.showError("Error while clearing list!", "dealalert.com")
}

showToasterErrorRemove(){
  this.notifyService.showError("Error while deleting product!", "dealalert.com")
}

showToasterErrorStart(){
  this.notifyService.showError("Provide valid alert threshold!", "dealalert.com")
}

showToasterErrorStop(){
  this.notifyService.showError("Alert already disabled!", "dealalert.com")
}

showToasterErrorHistory(){
  this.notifyService.showError("Error while loading price history!", "dealalert.com")
}


showToasterInfoWishlist(){
  this.notifyService.showInfo("Wishlist loaded successfully", "dealalert.com")
}

}