import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

import { CreateFriend, Friend } from './models/friend.model';

@Injectable({ providedIn: 'root' })
export class FriendService {
  private readonly http = inject(HttpClient);
  findAll() { return this.http.get<Friend[]>('/api/friends'); }
  create(friend: CreateFriend) {
    return this.http.post<Friend>('/api/friends', friend);
  }
}
