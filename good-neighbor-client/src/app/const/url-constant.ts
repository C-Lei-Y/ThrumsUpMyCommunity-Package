import { environment } from '../../environments/environment';

export class UrlConstant {
  public static readonly BASE = environment.baseUrl;

  public static readonly LOGIN = UrlConstant.BASE + '/login';
  public static readonly LOGOUT = UrlConstant.BASE + '/logout';
  public static readonly ACTUATOR_INFO = UrlConstant.BASE + '/actuator/info';

  public static readonly Global = class Global {
    public static readonly PREFIX = UrlConstant.BASE + '/global';

    public static readonly STATUS = Global.PREFIX + '/status';
    public static readonly PARAMETERS = Global.PREFIX + '/parameters';
    public static readonly LIKE = Global.PREFIX + '/likes';
    public static readonly LIKE_COUNT = Global.PREFIX + '/likes:count';
  };

  public static readonly Postit = class Postit {
    public static readonly PREFIX = UrlConstant.BASE + '/postit';

    public static readonly BOARDS = Postit.PREFIX + '/boards';
    public static readonly NOTES = Postit.PREFIX + '/notes';
    public static readonly NOTES_EXPORT = Postit.NOTES + ':export';
    public static readonly ATTACHED_FILES = Postit.PREFIX + '/attached-files';
  };

  public static readonly User = class User {
    public static readonly PREFIX = UrlConstant.BASE + '/users';

    public static readonly USERS = User.PREFIX;
    public static readonly CURRENT_USER = User.PREFIX + '/me';
    public static readonly ROLES = User.PREFIX + '/roles';
  };

  public static readonly WebSocket = class WebSocket {
    public static readonly CONNECTION = UrlConstant.BASE + '/websocket';

    public static readonly LISTEN_LIKE_COUNT = '/listen/likes:count';
  };
}
