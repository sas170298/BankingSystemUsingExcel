package com.verdantis.bank.dao;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.verdantis.bank.bo.custPojo;
import com.verdantis.bank.controller.*;

public class daoImpl {
	
	Workbook wb;	
	public static Sheet sheet1;		//main database
	public static Sheet sheet2;		//all transactions	
	public static File file=new File("workbook.xls");		
	public void createDatabase() throws FileNotFoundException, IOException
	{
		if(file.exists()==false)
		{			
            wb = new HSSFWorkbook();	    
    	    sheet1 = wb.createSheet("DATABASE SHEET"); 
    	    sheet2 = wb.createSheet("TRANSACTION SHEET");
    	     
    	   //creation of first sheet
    	    Row row = sheet1.createRow(0);		   				    
		    Cell cell = row.createCell(0);
		    cell.setCellValue("ID");						    
		    row.createCell(1).setCellValue("NAME");
		    row.createCell(2).setCellValue("ADDRESS");
		    row.createCell(3).setCellValue("BALANCE");
		    row.createCell(4).setCellValue("BRANCH ID");	
		    
		   //creation of second sheet
		    Row row2 = sheet2.createRow(0);		   				    
		    Cell cell2 = row2.createCell(0);
		    cell2.setCellValue("ACCOUNT NO");						    
		    row2.createCell(1).setCellValue("BALANCE");
		    row2.createCell(2).setCellValue("ACCOUNT");
		    row2.createCell(3).setCellValue("BALANCE");
		    
		    // Write the output to a file
		    try (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
		        wb.write(fileOut);
		    }
		catch(Exception e)
		{
			controller.log.error(e);
		}
   }
   else
   {
   	   wb = new HSSFWorkbook(new FileInputStream("workbook.xls")); 	   
       sheet1 = wb.getSheetAt(0);
       sheet2 = wb.getSheetAt(1);
   }
		
}

	
public Boolean checkAccountNo(int accNo) 
	{
	try {
		createDatabase();
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
		Iterator<Row> iterator = sheet1.iterator();        
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if(cell.getColumnIndex()==0) 
                {
                switch (cell.getCellType()) {
                    case Cell.CELL_TYPE_STRING:
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                    {
                    	int val=(int) cell.getNumericCellValue();
                    	if(val==accNo)
                    	{
                    		return true;
                    	}
                        break;
                    }
                	}
                }
              
            }
          }
        return false;
	}

public int getBalance(int accNo) 
{	
	try {
		createDatabase();
	} catch (FileNotFoundException e) {
		controller.log.error(e);
	} catch (IOException e) {
		controller.log.error(e);
	}
Iterator<Row> iterator = sheet1.iterator();        
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if(cell.getColumnIndex()==0) 
                {
                switch (cell.getCellType()) {
                
                    case Cell.CELL_TYPE_STRING:
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                    {
                    	int val=(int) cell.getNumericCellValue();
                    	if(val==accNo)
                    	{
                    		System.out.println("ACCOUNT NO. EXISTS");
                    		
                    		Row r=cell.getRow();
                    		Cell cellNew = r.getCell(3);                   		
                    		int value=(int) cellNew.getNumericCellValue();
                    		return value;                    		
                    	}
                        break;
                    }
                	}
                }
              
            }
          }
        return 0;
	}

	public void updateBalance(int accNo, int balance) {
		try {
			createDatabase();
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		Iterator<Row> iterator = sheet1.iterator();        
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if(cell.getColumnIndex()==0) 
                {
                switch (cell.getCellType()) {
                
                    case Cell.CELL_TYPE_STRING:
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                    {
                    	int val=(int) cell.getNumericCellValue();
                    	if(val==accNo)
                    	{
                    		
                    		Row r=cell.getRow();
                    		Cell cellNew = r.getCell(3);                   		
                    		cellNew.setCellValue(balance);
                    		                  		
                    		try (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
                    	        wb.write(fileOut);
                    	        fileOut.close();
                    	        
                    	    }
                    		catch(Exception e)
                    		{
                    			
                    		}                    		
                    	}
                        break;
                    }
                	}
                }              
            }
          }		
	}

	public void addCustomer(custPojo customer) 
	{
		try{	
			createDatabase();
		int	n=sheet1.getLastRowNum();  //to get last filled row	
		//System.out.println(n);  
		
		Row row1=sheet1.createRow(++n);
		
		int accNo=customer.getAccountNo();
		String custName=customer.getName();
		String address=customer.getAddress();
		int balance=customer.getBalance();
		int branchId=customer.getBranchId();		
		
		row1.createCell(0).setCellValue(accNo);
		row1.createCell(1).setCellValue(custName);
		row1.createCell(2).setCellValue(address);
		row1.createCell(3).setCellValue(balance );
		row1.createCell(4).setCellValue(branchId);
	//	putIntTransactFile(accNo, balance);
		try (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
	        wb.write(fileOut);
	        fileOut.close();
	    }
		catch(Exception e)
		{
			System.out.println("holla 1");
			e.printStackTrace();
		}
		}catch(Exception e)
		{
			System.out.println("holla 2");
			e.printStackTrace();
		}
	}

	public custPojo getDetail(int accNo) {
		try {
			createDatabase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		Iterator<Row> iterator = sheet1.iterator();        
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            Iterator<Cell> cellIterator = nextRow.cellIterator();
             
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                if(cell.getColumnIndex()==0) 
                {
                switch (cell.getCellType()) {
                
                    case Cell.CELL_TYPE_STRING:
                        break;
                    case Cell.CELL_TYPE_BOOLEAN:
                        break;
                    case Cell.CELL_TYPE_NUMERIC:
                    {
                    	int val=(int) cell.getNumericCellValue();
                    	if(val==accNo)
                    	{
                    		
                    		Row r=cell.getRow();
                    		Cell cellNew = r.getCell(1);                   		
                    		String name=cellNew.getStringCellValue();
                    		 cellNew = r.getCell(2);
                    		String address=cellNew.getStringCellValue();
                    		cellNew = r.getCell(3);
                    		int balance=(int) cellNew.getNumericCellValue();
                    		 cellNew = r.getCell(4);
                    		int branchId=(int) cellNew.getNumericCellValue();
                    		
                    		custPojo cust=new custPojo(accNo,name,address,balance,branchId);
                    		
                    		return cust;                    		                    	                   		
                    	}
                        break;
                    }
                	}
                }
              
            }
          }		
		return null;
	}

public void putIntTransactFile(int accNo1,  int balance1, int accNo2, int balance2 ) throws FileNotFoundException, IOException 
{
		try{			
			int	n=sheet2.getLastRowNum();     //to get last filled row	           	
			Row row=sheet2.createRow(++n);								
			row.createCell(0).setCellValue(accNo1);
			row.createCell(1).setCellValue(balance1);
			row.createCell(2).setCellValue(accNo2);
			row.createCell(3).setCellValue(balance2);
			try (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
		        wb.write(fileOut);
		        fileOut.close();
		    }
			}catch(Exception e)
			{
				controller.log.error(e);
			}
		
}

	public void putInSeparateTranFile(Document document, PdfPTable table, int accNo) {
		try{
			int n=sheet2.getLastRowNum();			
			while(n!=0)
			{				
				Row row=sheet2.getRow(n);				
				Cell cell=row.getCell(0);
				int value=(int) cell.getNumericCellValue();
				
				if(value==accNo)
				{	
					//int k=sheet2.getLastRowNum();
					//Row rowNew=table.createRow(++k);					
					//Cell cellNew1=rowNew.createCell(0);
					//cellNew1.setCellValue(value);					
					table.addCell("val "+value);
					Cell cell2=row.getCell(1);
					int value2=(int) cell2.getNumericCellValue();
					//Cell cellNew2=rowNew.createCell(1);
					//cellNew2.setCellValue(value2);
					table.addCell("val "+value2);
				}
				n--;
			}
	/*		for (int i=1;i<5;i++){
	    	     table.addCell("Name:"+i);
	             table.addCell("Age:"+i);
	             table.addCell("Location:"+i);
	          }*/
		  PdfWriter.getInstance(document, new FileOutputStream("tran"+accNo+".pdf"));
		  document.open();
	      document.add(table);
		  document.close();	
		}catch(Exception e)
		{
			controller.log.error(e);
		}
		}
	
	
	public  void showCustTransactions(int accNo) {
try {
	createDatabase();
} catch (FileNotFoundException e1) {
	e1.printStackTrace();
} catch (IOException e1) {
	e1.printStackTrace();
}
	
		/*SEPARATE PDF CREATION*/	
		String FILE="tran"+accNo+".pdf";
		 File file=new File(FILE);
		if(file.exists()==true)
		{
			file.delete();
		}
		PdfPTable table = null;
		try{
			
			table = new PdfPTable(new float[] { 2, 1, 2 ,1});
			table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
			  	  table.addCell("ACCOUNT");
		          table.addCell("BALANCE");
		          table.addCell("ACCOUNT 2");
		          table.addCell("BALANCE");
			  table.setHeaderRows(1);
			  PdfPCell[] cells = table.getRow(0).getCells(); 
			  for (int j=0;j<cells.length;j++){
			     cells[j].setBackgroundColor(BaseColor.GRAY);
			  }
			  int n=sheet2.getLastRowNum();
				
				
				while(n!=0)
				{
					
					Row row=sheet2.getRow(n);				
					Cell cell=row.getCell(0);
					int value=(int) cell.getNumericCellValue();
					
					Cell celll=row.getCell(2);
					int valuee=(int) celll.getNumericCellValue();
					
					if(value==accNo || valuee==accNo)
					{	
								
						table.addCell("val "+value);
						
						Cell cell1=row.getCell(1);
						int value1=(int) cell1.getNumericCellValue();					
						table.addCell("val "+value1);
						
						Cell cell2=row.getCell(2);
						int value2=(int) cell2.getNumericCellValue();					
						table.addCell("val "+value2);
						
						Cell cell3=row.getCell(3);
						int value3=(int) cell3.getNumericCellValue();					
						table.addCell("val "+value3);
					}
					n--;
				}		
			Document document=new Document();
			PdfWriter.getInstance(document, new FileOutputStream(FILE));
			  document.open();
		      document.add(table);
			  document.close();
			}catch(Exception e)
			{
				controller.log.error(e);
			}	
	}
	
}
