import { Component } from '@angular/core';
import { FriendsPage } from './features/friends/friends-page';

@Component({
  selector: 'app-root',
  imports: [FriendsPage],
  templateUrl: './app.html'
})
export class App {}
