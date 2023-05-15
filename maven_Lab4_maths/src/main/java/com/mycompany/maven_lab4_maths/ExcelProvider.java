package com.mycompany.maven_lab4_maths;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelProvider {
    private StatCalculations calculator = new StatCalculations();
    private ArrayList<double[]> samples = new ArrayList<>();
          
    public ArrayList<double[]> readFile(String path) throws IOException 
    {
        ArrayList<Double> sampleX = new ArrayList<>();
        ArrayList<Double> sampleY = new ArrayList<>();
        ArrayList<Double> sampleZ = new ArrayList<>();

        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(path));
        XSSFSheet sheet = workbook.getSheet("Вариант 10");
        Iterator<Row> iterator = sheet.iterator();

        while(iterator.hasNext()) 
        {
            Row row = iterator.next();
            Cell cellX = row.getCell(0);
            Cell cellY = row.getCell(1);
            Cell cellZ = row.getCell(2);

            if (cellX.getCellType() == CellType.NUMERIC) {
                sampleX.add(cellX.getNumericCellValue());
            }

            if (cellY.getCellType() == CellType.NUMERIC) {
                sampleY.add(cellY.getNumericCellValue());
            }

            if (cellZ.getCellType() == CellType.NUMERIC) {
                sampleZ.add(cellZ.getNumericCellValue());
            }
        }

        samples.add(sampleX.stream().mapToDouble(Double::doubleValue).toArray());
        samples.add(sampleY.stream().mapToDouble(Double::doubleValue).toArray());
        samples.add(sampleZ.stream().mapToDouble(Double::doubleValue).toArray());
        calculator.setSamples(samples);
        
        return samples;
    }

    public void writeToFile(DefaultTableModel table, DefaultTableModel cov_table, String pathToWrite) throws FileNotFoundException, IOException
    {
        XSSFWorkbook workbook1 = new XSSFWorkbook();
        XSSFSheet sheet1 = workbook1.createSheet("Calculations");
        Row headerRow1 = sheet1.createRow(0);
        for(int i=0; i<table.getColumnCount(); i++)
        {
            headerRow1.createCell(i).setCellValue(table.getColumnName(i));
        }
        for(int i=0; i<table.getRowCount(); i++)
        {
            XSSFRow row1 = sheet1.createRow(i+1);
            for(int j=0; j<table.getColumnCount(); j++)
            {
                XSSFCell cell1 = row1.createCell(j);
                cell1.setCellValue(table.getValueAt(i, j).toString());
            }
        }
        
        XSSFSheet sheet2 = workbook1.createSheet("Covariance matrix");
        Row headerRow2 = sheet2.createRow(0);
        for(int i=0; i<cov_table.getColumnCount(); i++)
        {
            headerRow2.createCell(i).setCellValue(cov_table.getColumnName(i));
        }
        for(int i=0; i<cov_table.getRowCount(); i++)
        {
            XSSFRow row2 = sheet2.createRow(i+1);
            for(int j=0; j<cov_table.getColumnCount(); j++)
            {
                XSSFCell cell2 = row2.createCell(j);
                cell2.setCellValue(cov_table.getValueAt(i, j).toString());
            }
        }
       
        workbook1.write(new FileOutputStream(pathToWrite));
        workbook1.close();
    }
}
