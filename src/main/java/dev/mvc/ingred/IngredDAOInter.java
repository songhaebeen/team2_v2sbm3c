package dev.mvc.ingred;

import java.util.ArrayList;

public interface IngredDAOInter {
	
	public int insert_ingred(IngredVO ingredvo);
	public ArrayList<IngredVO> ingred_list();

}
