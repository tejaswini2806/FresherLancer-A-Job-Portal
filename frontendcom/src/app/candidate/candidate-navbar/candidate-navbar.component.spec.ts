import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CandidateNavbarComponent } from './candidate-navbar.component';

describe('CandidateNavbarComponent', () => {
  let component: CandidateNavbarComponent;
  let fixture: ComponentFixture<CandidateNavbarComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CandidateNavbarComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CandidateNavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
