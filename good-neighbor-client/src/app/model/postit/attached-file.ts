import { IdEntity } from '../id-entity';

export class AttachedFile extends IdEntity {
  public postitNoteId!: number;
  public filename!: string;
  public size!: number;
  public type!: number;
}
