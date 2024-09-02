export class ArrayUtil {
  private constructor() {}

  public static removeElement<T>(array: Array<T>, predicate: (value: T, index: number, obj: T[]) => boolean): void {
    const indexFound = array.findIndex(predicate);
    if (indexFound > -1) {
      array.splice(indexFound, 1);
    }
  }
}
