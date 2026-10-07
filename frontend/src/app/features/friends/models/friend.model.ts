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
