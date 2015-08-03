package cn.zhangzuofeng.excel;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Excel Style风格工具类
 *
 * @author dsy
 * @version 1.0
 */
public class ExcelCellStyleUtils {

    //标题样式
    public static HSSFCellStyle titleStyle;
    //时间样式
    public static HSSFCellStyle dataStyle;
    //单元格样式
    public static HSSFCellStyle nameStyle;
    //超链接样式
    public static HSSFCellStyle linkStyle;
    public static HSSFFont font;

    public ExcelCellStyleUtils(HSSFWorkbook work) {
        titleStyle = titleStyle(work);
        dataStyle = dataStyle(work);
        nameStyle = nameStyle(work);
        linkStyle = linkStyle(work);
    }

    /**
     * 超链接样式
     *
     * @return HSSFCellStyle
     */
    private static HSSFCellStyle linkStyle(HSSFWorkbook work) {
        HSSFCellStyle linkStyle = work.createCellStyle();
        linkStyle.setBorderBottom((short) 1);
        linkStyle.setBorderLeft((short) 1);
        linkStyle.setBorderRight((short) 1);
        linkStyle.setBorderTop((short) 1);
        linkStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        linkStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        HSSFFont font = work.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setUnderline((byte) 1);
        font.setColor(HSSFColor.BLUE.index);
        linkStyle.setFont(font);
        return linkStyle;
    }

    /**
     * s
     * 单元格样式
     *
     * @return HSSFCellStyle
     */
    private static HSSFCellStyle nameStyle(HSSFWorkbook work) {
        HSSFCellStyle nameStyle = work.createCellStyle();
        nameStyle.setBorderBottom((short) 1);
        nameStyle.setBorderLeft((short) 1);
        nameStyle.setBorderRight((short) 1);
        nameStyle.setBorderTop((short) 1);
        nameStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
        nameStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        return nameStyle;
    }

    /**
     * 时间样式
     *
     * @return HSSFCellStyle
     */
    private static HSSFCellStyle dataStyle(HSSFWorkbook work) {
        HSSFCellStyle dataStyle = work.createCellStyle();
        dataStyle.setBorderBottom((short) 1);
        dataStyle.setBorderLeft((short) 1);
        dataStyle.setBorderRight((short) 1);
        dataStyle.setBorderTop((short) 1);
        dataStyle.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        dataStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        dataStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        return dataStyle;
    }

    /**
     * 标题样式
     *
     * @return HSSFCellStyle
     */
    private static HSSFCellStyle titleStyle(HSSFWorkbook work) {
        HSSFCellStyle titleStyle = work.createCellStyle();
        font = work.createFont();
        //		font.setFontHeightInPoints((short) 12);//设置字体大小
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        titleStyle.setBorderBottom(HSSFCellStyle.BORDER_DOUBLE);
        titleStyle.setBorderLeft((short) 1);
        titleStyle.setBorderRight((short) 1);
        titleStyle.setBorderTop(HSSFCellStyle.BORDER_DOUBLE);
        titleStyle.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
        titleStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        titleStyle.setFont(font);
        return titleStyle;
    }
}
