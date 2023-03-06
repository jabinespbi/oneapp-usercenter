import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UserService} from "../../services/user.service";
import {AuthService} from "../../services/auth.service";
import {User} from "../../models/user";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.less']
})
export class UserProfileComponent implements OnInit {
  static navigationPath: string = 'user-profile';

  mainSettingsGroup: FormGroup;
  user: User | any;

  isMessageHidden: boolean = true;
  message: string = '';
  alertType: string = '';

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private authService: AuthService,
              private activatedRoute: ActivatedRoute) {
    this.activatedRoute.data.subscribe(data => {
      console.log(data);
    });

    this.mainSettingsGroup = formBuilder.group({
      fullName: [''],
      pronouns: [''],
      pronunciation: [''],
      email: [''],
      location: [''],
      jobTitle: [''],
      organization: [''],
      skype: [''],
      linkedin: [''],
      twitter: [''],
      facebook: [''],
      websiteUrl: [''],
      bio: [''],
    });
  }

  ngOnInit(): void {
    this.authService.getCurrentlyAuthenticatedUser().subscribe(response => {
      if (!response.body) {
        throw new Error('Undefined response body!');
      }

      this.user = response.body;
      this.setMainSettingsGroupWith(this.user);
    });
  }

  onSubmit(): void {
    const fullName = this.mainSettingsGroup.get('fullName')?.value;
    const pronouns = this.mainSettingsGroup.get('pronouns')?.value;
    const pronunciation = this.mainSettingsGroup.get('pronunciation')?.value;
    const email = this.mainSettingsGroup.get('email')?.value;
    const location = this.mainSettingsGroup.get('location')?.value;
    const jobTitle = this.mainSettingsGroup.get('jobTitle')?.value;
    const organization = this.mainSettingsGroup.get('organization')?.value;
    const skype = this.mainSettingsGroup.get('skype')?.value;
    const linkedin = this.mainSettingsGroup.get('linkedin')?.value;
    const twitter = this.mainSettingsGroup.get('twitter')?.value;
    const facebook = this.mainSettingsGroup.get('facebook')?.value;
    const websiteUrl = this.mainSettingsGroup.get('websiteUrl')?.value;
    const bio = this.mainSettingsGroup.get('bio')?.value;

    this.userService.update(
      fullName,
      pronouns,
      pronunciation,
      email,
      location,
      bio,
      jobTitle,
      organization,
      skype,
      linkedin,
      twitter,
      facebook,
      websiteUrl).subscribe({
      next: (response) => {
        if (!response.body) {
          this.message = 'An internal error occurred! Please email for support: jabinespbi@gmail.com';
          this.isMessageHidden = false;
          this.alertType = 'danger';

          throw new Error('Undefined response body!');
        }

        this.message = 'User profile successfully updated!';
        this.isMessageHidden = false;
        this.alertType = 'success';
        this.user = response.body;
      },
      error: (error) => {
        this.message = error.errorMessage;
        this.isMessageHidden = false;
        this.alertType = 'danger';
      }
    });
  }

  onCancel() {
    this.setMainSettingsGroupWith(this.user);
  }

  private setMainSettingsGroupWith(user: User) {
    this.mainSettingsGroup.get('fullName')?.setValue(user.fullName);
    this.mainSettingsGroup.get('pronouns')?.setValue(user.pronouns);
    this.mainSettingsGroup.get('pronunciation')?.setValue(user.pronunciation);
    this.mainSettingsGroup.get('email')?.setValue(user.email);
    this.mainSettingsGroup.get('location')?.setValue(user.location);
    this.mainSettingsGroup.get('jobTitle')?.setValue(user.jobTitle);
    this.mainSettingsGroup.get('organization')?.setValue(user.organization);
    this.mainSettingsGroup.get('skype')?.setValue(user.skype);
    this.mainSettingsGroup.get('linkedin')?.setValue(user.linkedin);
    this.mainSettingsGroup.get('twitter')?.setValue(user.twitter);
    this.mainSettingsGroup.get('facebook')?.setValue(user.facebook);
    this.mainSettingsGroup.get('websiteUrl')?.setValue(user.websiteUrl);
    this.mainSettingsGroup.get('bio')?.setValue(user.bio);
  }
}
