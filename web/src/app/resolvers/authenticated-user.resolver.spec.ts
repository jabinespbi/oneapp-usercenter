import { TestBed } from '@angular/core/testing';

import { AuthenticatedUserResolver } from './authenticated-user.resolver';

describe('AuthenticatedUserResolver', () => {
  let resolver: AuthenticatedUserResolver;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    resolver = TestBed.inject(AuthenticatedUserResolver);
  });

  it('should be created', () => {
    expect(resolver).toBeTruthy();
  });
});
