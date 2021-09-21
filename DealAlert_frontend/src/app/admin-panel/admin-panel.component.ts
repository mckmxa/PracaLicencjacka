import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DbUpdateService } from '../services/db-update.service';
import { User } from '../models/user.model'
import { UserService } from '../services/user.service';
import { NotificationService } from '../services/notification.service';

@Component({
  selector: 'app-admin-panel',
  templateUrl: './admin-panel.component.html',
  styleUrls: ['./admin-panel.component.css']
})
export class AdminPanelComponent implements OnInit {
  public users: User[];
  public editUser: User;
  public deleteUser: User;

  constructor(private dbUpdateService : DbUpdateService, private userService: UserService, private notifyService: NotificationService) { }

  ngOnInit(): void {
    this.retrieveUsers();
  }

  public retrieveUsers(): void {
    this.userService.getUsers().subscribe(
      (response: User[]) => {
        this.users = response;
        console.log(this.users);
      },
      (error: HttpErrorResponse) => {
        this.showToasterErrorRetrieve();

      }
    );
  }



  public onUpdateUser(user: User): void {
    this.userService.updateUser(user).subscribe(
      (response: User) => {
        console.log(response);
        this.retrieveUsers();
        this.showToasterSuccessEdit();
      },
      (error: HttpErrorResponse) => {
        this.showToasterErrorEdit();
      }
    );
  }

  public onDeleteUser(userId: string): void {
    this.userService.deleteUser(userId).subscribe(
      (response: void) => {
        console.log(response);
        this.retrieveUsers();
        this.showToasterSuccessDelete();
      },
      (error: HttpErrorResponse) => {
        this.showToasterErrorDelete();
      }
    );
  }

  public searchUsers(key: string): void {
    console.log(key);
    const results: User[] = [];
    for (const user of this.users) {
      if (user.username.toLowerCase().indexOf(key.toLowerCase()) !== -1
      || user.email.toLowerCase().indexOf(key.toLowerCase()) !== -1)
      {
        results.push(user);
      }
    }
    this.users = results;
    if (results.length === 0 || !key) {
      this.retrieveUsers();
    }
  }

  public onOpenModal(user: User, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');
    if (mode === 'edit') {
      this.editUser = user;
      button.setAttribute('data-target', '#updateUserModal');
    }
    if (mode === 'delete') {
      this.deleteUser = user;
      button.setAttribute('data-target', '#deleteUserModal');
    }
    container?.appendChild(button);
    button.click();
  }



  updateDB(){
    this.dbUpdateService.updateDB().subscribe(data => {
      this.showToasterSuccessUpdateDB();
    },err=> {
      this.showToasterErrorUpdateDB();
    }
      
       
       )
       
  }

  // CUSTOMIZING ALERTS FOR THIS COPONENT //

  showToasterSuccessDelete(){
    this.notifyService.showSuccess("Successfully deleted user", "dealalert.com")
  }

  showToasterSuccessEdit(){
    this.notifyService.showSuccess("Successfully edited user", "dealalert.com")
  }

  showToasterSuccessUpdateDB(){
    this.notifyService.showSuccess("Successfully updated database", "dealalert.com")
  }


  showToasterErrorDelete(){
    this.notifyService.showError("Error while deleting user!", "dealalert.com")
  }

  showToasterErrorEdit(){
    this.notifyService.showError("Error while editing user!", "dealalert.com")
  }

  showToasterErrorRetrieve(){
    this.notifyService.showError("Error while loading users!", "dealalert.com")
  }

  showToasterErrorUpdateDB(){
    this.notifyService.showError("Error while updating database!", "dealalert.com")
  }
}