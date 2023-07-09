package dev.mvc.good;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("dev.mvc.good.GoodProc")
public class GoodProc implements GoodProcInter{
  @Autowired
  private GoodDAOInter goodDAO;
  
	@Override
	public int findGood(GoodVO goodVO) {
		int cnt =this.goodDAO.findGood(goodVO);
		return cnt;
	}

	@Override
	public int up(int goodno) {
		int cnt = this.goodDAO.up(goodno);
		return cnt;
	}

	@Override
	public int down(int goodno) {
		int cnt = this.goodDAO.down(goodno);
		return cnt;
	}

}
