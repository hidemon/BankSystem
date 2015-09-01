package util;

import java.util.HashMap;
import java.util.LinkedList;

import db.DB1;

public class PageBean {
//	javaBean
	
	private int page ;//当前页数
	private int maxPage ;//最大页数
	private int maxRow ;//最大条数
//	private int nextPage ;//下一页是哪
//	private int prePage ;//上一页是哪
	private int pageSize = 5;//每页条数
//	private String sql ;//sql语句
	
	private LinkedList<HashMap<String,String>> list ; 
	
	public PageBean(String sql ,int page ,int pageSize){
//		根据sql语句和页数，以及每页条数，得到需要的数据
	}
	
	public PageBean(String sql ,int page ){
		
//		判断page是否小于1
		if(page < 1){
			page = 1 ;
		}
		
//		求最大页数，判断page是否越界
		
		String sqlCount = " SELECT COUNT(*) as num FROM (   "+ sql +" ) AS t " ;
//		System.out.println(sqlCount);
		DB1 d = new DB1();
		LinkedList<HashMap<String, String>> listCount = d.queryToList(sqlCount);//list存储的是总记录数
		if(listCount.size()>0){
			maxRow = Integer.parseInt(listCount.get(0).get("num"));//总记录数
//			System.out.println("maxRow------"+maxRow);
			maxPage = maxRow % pageSize == 0 ?maxRow / pageSize : maxRow / pageSize +1 ;//总页数
//			System.out.println("maxPage-------------"+maxPage);
			if(maxPage==0){
				maxPage=1;
			}
			if( page > maxPage ){
				page = maxPage;
			}
			
			this.page = page;
			
			//从第几条开始
			int start = ( page-1 ) * pageSize;
			
			sql = sql+ " limit "+start+" ,"+pageSize;
//			System.out.println("sql-----------------------------------"+sql);
			list = d.queryToList(sql);
		}
		d.close();
		
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public LinkedList<HashMap<String, String>> getList() {
		return list;
	}
	
	//page读取权限，写入的权限
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getMaxPage() {
		return maxPage;
	}
	public void setMaxPage(int maxPage) {
		this.maxPage = maxPage;
	}
	public int getMaxRow() {
		return maxRow;
	}
	public void setMaxRow(int maxRow) {
		this.maxRow = maxRow;
	}
	
	
	
}