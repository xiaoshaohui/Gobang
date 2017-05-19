import java.io.*;
import java.util.Arrays;
import java.util.regex.*;
import java.util.*;

public class Gobang  
{
	//定义棋盘的大小
	private static int BOARD_SIZE=15;
	//定义一个二维数组来充当棋盘
	private String[][] board;
	public void initBoard()
	{
		//初始化棋盘数组
		board=new String[BOARD_SIZE][BOARD_SIZE];
		//把每个元素设为"十"，在控制台上画出棋盘
		for(int i=0;i<BOARD_SIZE;i++)
		{
			for (int j=0;j<BOARD_SIZE ;j++ )
			{
				board[i][j]="十";
			}
		}
	}

	//输出棋盘的方法
	public void printBoard()
	{
		for(int i=0;i<BOARD_SIZE;i++)
		{
			for (int j=0;j<BOARD_SIZE ;j++ )
			{
				System.out.print(board[i][j]);
			}
			System.out.print("\n");
		}
	}

	//直线方向判断是否赢棋,label是区分不同棋手所使用棋子的标志
	public boolean linearJudgment(int x,int y,String label)
	{
		//横向判断
		//确定搜索范围
		int rangeOfLeft=y-4;
		int rangeOfRight=y+4;
		//确保搜索范围不出界
		if (rangeOfLeft<0)
		{
			rangeOfLeft=0;
		}
		if (rangeOfRight>14)
		{
			rangeOfRight=14;
		}
		//取出横向的棋子数据
		int horizontalLength=rangeOfRight-rangeOfLeft+1;
		int horizontalIndex=0;//判断指数
		String[] horizontalStr=new String[horizontalLength];
		for (int i=0;i<horizontalLength ;i++ )
		{
			horizontalStr[i]=board[x][i+rangeOfLeft];
		}
		//判断这些棋子是否有五子连珠
		for (int i=0;i<horizontalLength ;i++ )
		{
			if (horizontalStr[i]==label)
			{
				horizontalIndex++;
			}
			else 
			{
				horizontalIndex=0;
			}
			if (horizontalIndex==5)
			{
				break;
			}
		}

		//纵向判断
		//确定搜索范围
		int rangeOfHigh=x-4;
		int rangeOfLow=x+4;
		//确保搜索范围不出界
		if (rangeOfHigh<0)
		{
			rangeOfHigh=0;
		}
		if (rangeOfLow>14)
		{
			rangeOfLow=14;
		}
		//取出纵向的棋子数据
		int verticalLength=rangeOfLow-rangeOfHigh+1;
		int verticalIndex=0;//判断指数
		String[] verticalStr=new String[verticalLength];
		for (int i=0;i<verticalLength ;i++ )
		{
			verticalStr[i]=board[i+rangeOfHigh][y];
		}
		//判断这些棋子是否有五子连珠
		for (int i=0;i<verticalLength ;i++ )
		{
			if (verticalStr[i]==label)
			{
				verticalIndex++;
			}
			else 
			{
				verticalIndex=0;
			}
			if (verticalIndex==5)
			{
				break;
			}
		}

		//综合判断是否直线方向上赢了
		if (horizontalIndex==5||verticalIndex==5)
		{
			return true;
		}
		else
			return false;
	}

	//斜线方向判断是否赢棋,label是区分不同棋手所使用棋子的标志
	//这种可以直接把一条斜线上的棋子数据都取出来
	public boolean slashJudgment(int x,int y,String label)
	{
		//左斜方向判断
		//确定左斜方向搜索范围
		int leftSlashIndex=0;
		int leftSlashLength=15-Math.abs(x-y);
		String[] leftSlashStr=new String[leftSlashLength];
		//找出x，y坐标的最小值
		int min=-1;
		if (x<y)
		{
			min=x;
		}
		else
		{
			min=y;
		}
		//确定左斜的开始坐标
		int xLeftSlash=x-min;
		int yLeftSlash=y-min;
		//取出左斜上的棋子数据
		for (int i=0;i<leftSlashLength ;i++ )
		{
			leftSlashStr[i]=board[i+xLeftSlash][i+yLeftSlash];
		}
		//判断左斜这些棋子是否有五子连珠
		for (int i=0;i<leftSlashLength ;i++ )
		{
			if (leftSlashStr[i]==label)
			{
				leftSlashIndex++;
			}
			else 
			{
				leftSlashIndex=0;
			}
			if (leftSlashIndex==5)
			{
				break;
			}
		}

		//右斜方向判断
		//确定右斜方向搜索长度,BOARD_SIZE=15
		int rightSlashIndex=0;
		int rightSlashLength=BOARD_SIZE-Math.abs(14-x-y);
		String[] rightSlashStr=new String[rightSlashLength];
		//取出右斜上的棋子数据
		for (int i=0;i<rightSlashLength ;i++ )
		{
			for (int j=0;j<rightSlashLength ;j++ )
			{
				if (i+j==x+y)
				{
					rightSlashStr[i]=board[i][j];
				}
			}
		}
		//判断右斜这些棋子是否有五子连珠
		for (int i=0;i<rightSlashLength ;i++ )
		{
			if (rightSlashStr[i]==label)
			{
				rightSlashIndex++;
			}
			else 
			{
				rightSlashIndex=0;
			}
			if (rightSlashIndex==5)
			{
				break;
			}
		}

		//综合判断是否斜线方向上赢了
		if (leftSlashIndex==5||rightSlashIndex==5)
		{
			return true;
		}
		else
			return false;
	}


	//使用正则表达式判断输入是否合法
	public boolean isInputValid(String inputStr)
	{
		String regexStr="([1-9]|[1]\\d(?<!1[6-9])),([1-9]|[1]\\d(?<!1[6-9]))";
		Pattern patternStr=Pattern.compile(regexStr);
		Matcher matcher=patternStr.matcher(inputStr);
		return matcher.matches();
	}


	//主函数
	public static void main(String[] args)  throws Exception
	{
		//取得系统默认的国家和语言环境
		Locale myLocale=Locale.getDefault(Locale.Category.FORMAT);
		//根据国家加载不同的资源文件
		ResourceBundle bundle=ResourceBundle.getBundle("Gobang",myLocale);

		Gobang gb=new Gobang();
		gb.initBoard();
		gb.printBoard();
		//获取键盘输入
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String inputStr=null;
		System.out.println(bundle.getString("轮到用户下棋---------------------"));
		System.out.println(bundle.getString("请输入您下棋的坐标，应以x,y格式："));
		while ((inputStr=br.readLine())!=null)
		{
			//判断输入格式是否有误
			if(!gb.isInputValid(inputStr))
			{
				System.out.println(bundle.getString("输入棋子坐标格式错误，应以x,y格式且范围为(1-15)，请重新输入："));
				continue;
			}
			//把输入的坐标分割成xy坐标
			String[] pasStrArr=inputStr.split(",");
			//用户下棋的坐标
			int xPos=Integer.parseInt(pasStrArr[0]);
			int yPos=Integer.parseInt(pasStrArr[1]);
			//计算机进行处理的坐标
			int x=-1;
			int y=-1;
			int xCom=-1;
			int yCom=-1;
			//检测当前此处是否存在棋子
			//进行异常处理
			try
			{
				if (gb.board[yPos-1][xPos-1]!="十")
				{
					System.out.println(bundle.getString("输入棋子坐标错误，此处已有棋子，请重新输入："));
					continue;
				}
			}
			catch(IndexOutOfBoundsException ioobe)
			{
				System.out.println(bundle.getString("输入棋子坐标格式错误，应以x,y格式且范围为(1-15)，请重新输入："));
				continue;
			}
			//"●"为用户，"○"为计算机
			gb.board[yPos-1][xPos-1]="●";
			//每个用户下完后重新打印棋盘
			gb.printBoard();
			x=yPos-1;
			y=xPos-1;
			//需要扫描进行判断是否出现用户赢棋的情况
			//我觉得不错的判断方法是每次输入一个新的棋子时对这个棋子能否构成赢棋进行判断
			if (gb.linearJudgment(x,y,"●")||gb.slashJudgment(x,y,"●"))
			{
				System.out.println(bundle.getString("用户赢了！"));
				System.out.println(bundle.getString("请输入Y/N确定是否退出，输入Y则退出，输入N则重新开始："));
				inputStr=br.readLine();
				if (inputStr.equals("Y"))
				{
					return;
				}
				else
				{
					gb.initBoard();
					gb.printBoard();
					System.out.println(bundle.getString("轮到用户下棋---------------------"));
					System.out.println(bundle.getString("请输入您下棋的坐标，应以x,y格式："));
					continue;
				}
			}
			//此处随机生成两个坐标作为电脑的棋子坐标，坐标范围为[1,16)的整数
			System.out.println(bundle.getString("轮到电脑下棋---------------------"));
			int xPosCom=-1;
			int yPosCom=-1;
			//检测当前此处是否存在棋子
			do
			{
				xPosCom=(int)(Math.random()*15+1);
				yPosCom=(int)(Math.random()*15+1);
			}
			while (gb.board[yPosCom-1][xPosCom-1]!="十");
			gb.board[yPosCom-1][xPosCom-1]="○";
			//需要扫描进行判断是否出现电脑赢棋的情况
			//重新打印棋盘以及棋子
			gb.printBoard();
			xCom=yPosCom-1;
			yCom=xPosCom-1;
			if (gb.linearJudgment(xCom,yCom,"○")||gb.slashJudgment(xCom,yCom,"○"))
			{
				System.out.println(bundle.getString("电脑赢了！继续下一局："));				
				gb.initBoard();
				gb.printBoard();
			}
			System.out.println(bundle.getString("轮到用户下棋---------------------"));
			System.out.println(bundle.getString("请输入您下棋的坐标，应以x,y格式："));
		}
	}
}
