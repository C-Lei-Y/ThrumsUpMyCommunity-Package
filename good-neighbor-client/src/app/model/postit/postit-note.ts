import { NamedEntity } from '../named-entity';
import { AttachedFile } from './attached-file';

export class PostitNote extends NamedEntity {
  public text!: string;
  public boardId!: number;
  public color!: string;
  public orderNum!: number;
  public attachedFile!: AttachedFile | null;
}
