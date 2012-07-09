package models;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.avaje.ebean.ExpressionList;

import play.data.*;
import play.data.validation.*;
import play.db.ebean.Model;
import play.db.ebean.Model.*;

@Entity
public class Reply extends Model{
	
	public Reply() {
		date = new Date();
	}
	
	@Id
	public int replyNum;
	
	@Constraints.Required
	public int articleNum;
	
	@Constraints.Required
	public String contents;
	
	@Constraints.Required
	public String writer;
	
	@Constraints.Required
	public Date date;
	
	
	//TODO File attach 기능 추가해야함.
	private static Finder<Long, Reply> find = new Finder<Long, Reply>(Long.class, Reply.class);
	
	public static List<Reply> findArticlesReply(int articleNum)
	{
		return find.where().eq("articleNum", articleNum).findList();
	}
	public static void write(Reply reply)
	{
		reply.save();
	}
	public static void deleteByArticleNum(int articleNum)
	{
		List<Reply> targets = Reply.find.where().eq("articleNum", "" + articleNum).findList();
		
		//루프 돌면서 삭제
		
	}
	
	public String calcPassTime()
	{
		//TODO 경계값 검사하면 망할함수. 나중에 라이브러리 쓸예정
		Calendar today = Calendar.getInstance();
		
		long dTimeMili = today.getTime().getTime() - this.date.getTime();
		
		
		Calendar dTime = Calendar.getInstance();
		dTime.setTimeInMillis(dTimeMili);
		
		if(dTimeMili < 60* 1000)
		{
			return "방금 전";
		}
		else if(dTimeMili < 60 * 1000 * 60)
		{
			return dTime.get(Calendar.MINUTE) + "분 전";
		}
		else if(dTimeMili < 60 * 1000 * 60 * 24)
		{
			return dTime.get(Calendar.HOUR) + "시간 전";
		}
		else if(dTimeMili < 60 * 1000 * 60 * 24 * 30)
		{
			return dTime.get(Calendar.DATE) + "일 전";
		}
		else if(dTimeMili < 60 * 1000 * 60 * 24 * 30 * 12)
		{
			return dTime.get(Calendar.MONDAY) + "달 전"; 
		}
		else 
		{
			return dTime.get(Calendar.YEAR) + "년 전";
		}
	}
}
