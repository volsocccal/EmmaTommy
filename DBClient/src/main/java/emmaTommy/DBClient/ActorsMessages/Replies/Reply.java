package emmaTommy.DBClient.ActorsMessages.Replies;

public class Reply {
	protected String replyTypeName = this.getClass().getSimpleName();
	public Reply () {
	}
	public String getReplyTypeName() {
		return this.replyTypeName;
	}
}
