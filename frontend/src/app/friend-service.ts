import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface CreateFriend {
  firstname: string;
  lastname: string;
  notes: string;
}

export interface Friend extends CreateFriend {
  id: number;
  nickname: string | null;
  birthday: string | null;
  createdAt: string;
  updatedAt: string;
}

@Injectable({ providedIn: 'root' })
export class FriendService {
  private readonly http = inject(HttpClient);
  findAll() { return this.http.get<Friend[]>('/api/friends'); }
  create(friend: CreateFriend) {
    return this.http.post<Friend>('/api/friends', friend);
  }
}