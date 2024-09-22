import { Directive, Input, ViewContainerRef, OnInit, TemplateRef } from '@angular/core';

import { AuthService } from '../service/auth/auth.service';

@Directive({
  selector: '[appHasRole]',
})
export class HasRoleDirective implements OnInit {
  @Input()
  public appHasRole: string | Array<string> | null = null;

  public constructor(
    private viewContainerRef: ViewContainerRef,
    private templateRef: TemplateRef<any>,
    private authService: AuthService
  ) {}

  public ngOnInit(): void {
    const roleList = [];
    if (Array.isArray(this.appHasRole)) {
      roleList.push(...this.appHasRole);
    } else if (this.appHasRole) {
      roleList.push(this.appHasRole);
    }

    this.authService.userHasRoles(roleList).subscribe((hasRoles) => {
      if (hasRoles) {
        this.viewContainerRef.createEmbeddedView(this.templateRef);
      } else {
        this.viewContainerRef.clear();
      }
    });
  }
}
