import { IdEntity } from '../id-entity';

export class User extends IdEntity {
  username!: string;
  password?: string;
  enabled!: boolean;
  roleList!: Array<string>;
}
