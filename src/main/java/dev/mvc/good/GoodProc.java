package dev.mvc.good;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
;

@Component("dev.mvc.good.GoodProc")
public class GoodProc implements GoodProcInter{
  @Autowired
  private GoodDAOInter goodDAO;

	@Override
	public int create(GoodVO goodVO) {
		int cnt = this.goodDAO.create(goodVO);
		return cnt;
	}
	
  @Override
  public List<GoodVO> list_all() {
    List<GoodVO> list= this.goodDAO.list_all();
    return list;
  }
  
  @Override
  public List<GoodVO> list_memberno(int memberno) {
    List<GoodVO> list= this.goodDAO.list_memberno(memberno);
    return list;
  }

	@Override
	public int delete(GoodVO goodVO) {
		int cnt = this.goodDAO.delete(goodVO);
		return cnt;
	}
	
	 @Override
	  public int findGood(GoodVO goodVO) {
	    int cnt =this.goodDAO.findGood(goodVO);
	    return cnt;
	  }

}
