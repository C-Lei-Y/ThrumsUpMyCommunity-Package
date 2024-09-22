import { Directive, ElementRef, Renderer2, HostListener, Input } from '@angular/core';

/*
 * This directive is an example, this action can be done with just CSS instead
 */
@Directive({
  selector: '[appHighlight]',
})
export class HighlightDirective {
  @Input()
  get appHighlight(): string {
    return this.privAppHighlight;
  }
  set appHighlight(value: string) {
    if (value) {
      this.privAppHighlight = value;
    }
  }
  private privAppHighlight = '0 8px 8px rgba(10,16,20,.24),0 0 8px rgba(10,16,20,.12)';

  public constructor(private el: ElementRef, private renderer: Renderer2) {
    this.renderer.setStyle(this.el.nativeElement, 'transition', 'box-shadow .5s');
  }

  @HostListener('mouseenter')
  private onMouseEnter(): void {
    this.renderer.setStyle(this.el.nativeElement, 'box-shadow', this.appHighlight);
  }

  @HostListener('mouseleave')
  private onMouseLeave(): void {
    this.renderer.removeStyle(this.el.nativeElement, 'box-shadow');
  }
}
