import { Component, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { FriendService } from './friend-service';
import { Friend } from './models/friend.model';

@Component({
  selector: 'app-friends-page',
  imports: [ReactiveFormsModule],
  templateUrl: './friends-page.html',
  styleUrl: './friends-page.css'
})
export class FriendsPage implements OnInit {
  private readonly api = inject(FriendService);
  private readonly fb = inject(FormBuilder);
  readonly friends = signal<Friend[]>([]);
  readonly saving = signal(false);
  readonly message = signal('');
  readonly form = this.fb.nonNullable.group({
    firstname: ['', [Validators.required, Validators.pattern(/\S/), Validators.maxLength(255)]],
    lastname: ['', Validators.maxLength(255)],
    notes: ['', Validators.maxLength(1000)]
  });

  ngOnInit() {
    this.api.findAll().subscribe({
      next: friends => this.friends.set(friends),
      error: () => this.message.set('Impossible de charger les amis.')
    });
  }

  save() {
    if (this.form.invalid || this.saving()) {
      this.form.markAllAsTouched();
      return;
    }
    this.saving.set(true);
    this.message.set('');
    const values = this.form.getRawValue();
    this.api.create({ ...values, firstname: values.firstname.trim() }).subscribe({
      next: friend => {
        this.friends.update(friends => [...friends, friend]);
        this.form.reset();
        this.message.set('Ami enregistré.');
        this.saving.set(false);
      },
      error: () => {
        this.message.set('Enregistrement impossible. Vérifie les données et le serveur.');
        this.saving.set(false);
      }
    });
  }
}
