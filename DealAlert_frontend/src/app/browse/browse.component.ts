import { Component, OnInit } from '@angular/core';
import { ItemService } from '../services/item.service';
import { Item } from 'src/app/models/item.model';
import { ActivatedRoute, Router } from '@angular/router';
import { NotificationService } from '../services/notification.service';



@Component({
  selector: 'app-browse',
  templateUrl: './browse.component.html',
  styleUrls: ['./browse.component.css']
})

export class BrowseComponent implements OnInit {
  content?: string;
  items: Item[] = [];
  name = '';
  page = 1;
  count = 0;
  pageSize = 4;
  pageSizes = [4, 12, 16];




  constructor(private itemService : ItemService, private notifyService: NotificationService) { }

  ngOnInit(): void {
    this.retrieveItems();
  }

  retrieveItems(): void {
    const params = this.getRequestParams(this.name, this.page, this.pageSize);
    this.itemService.getAll(params)
      .subscribe(
        data => {
          const { items, totalItems } = data;

          for (const val of items) {
            val.name = val.name?.slice(0,50) + "..."
           }

          this.items = items;
          this.count = totalItems;
          console.log(data);
        },
        error => {
          console.log(error);
        });
  }

  getRequestParams(searchName: string, page: number, pageSize: number): any {
    let params: any = {};

    if (searchName) {
      params[`name`] = searchName;
    }

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveItems();
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.page = 1;
    this.retrieveItems();
  }



  searchName(): void {
    this.itemService.findByName(this.name)
      .subscribe(
        data => {
          this.items = data;
          console.log(data);
        },
        error => {
          console.log(error);
        });
        this.retrieveItems();
  }


  addToList(id:String): void {
    this.itemService.addToList(id)
      .subscribe(
        response => {
          console.log(response);
          this.showToasterSuccess();

        },
        error => {
          console.log(error);
          this.showToasterError();

        });
  }

  public searchProducts(key: string): void {
    console.log(key);
    const results: Item[] = [];
    for (const item of this.items) {
      if (item.name?.toLowerCase().indexOf(key.toLowerCase()) !== -1)
      {
        results.push(item);
      }
    }
    this.items = results;
    if (results.length === 0 || !key) {
      this.retrieveItems();
    }
  }
  

// CUSTOMIZING ALERTS FOR THIS COMPONENT //
showToasterSuccess(){
    this.notifyService.showSuccess("Successfully added", "dealalert.com")
}

showToasterError(){
    this.notifyService.showError("Product is already on your list!", "dealalert.com")
}


showToasterInfoWelcome(){
  this.notifyService.showInfo("Welcome!", "dealalert.com")
}



}