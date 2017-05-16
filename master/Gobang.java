import java.io.*;
import java.util.Arrays;

public class Gobang  
{
	//�������̵Ĵ�С
	private static int BOARD_SIZE=15;
	//����һ����ά�������䵱����
	private String[][] board;
	public void initBoard()
	{
		//��ʼ����������
		board=new String[BOARD_SIZE][BOARD_SIZE];
		//��ÿ��Ԫ����Ϊ"ʮ"���ڿ���̨�ϻ�������
		for(int i=0;i<BOARD_SIZE;i++)
		{
			for (int j=0;j<BOARD_SIZE ;j++ )
			{
				board[i][j]="ʮ";
			}
		}
	}

	//������̵ķ���
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

	//ֱ�߷����ж��Ƿ�Ӯ��,label�����ֲ�ͬ������ʹ�����ӵı�־
	public boolean linearJudgment(int x,int y,String label)
	{
		//�����ж�
		//ȷ��������Χ
		int rangeOfLeft=y-4;
		int rangeOfRight=y+4;
		//ȷ��������Χ������
		if (rangeOfLeft<0)
		{
			rangeOfLeft=0;
		}
		if (rangeOfRight>14)
		{
			rangeOfRight=14;
		}
		//ȡ���������������
		int horizontalLength=rangeOfRight-rangeOfLeft+1;
		int horizontalIndex=0;//�ж�ָ��
		String[] horizontalStr=new String[horizontalLength];
		for (int i=0;i<horizontalLength ;i++ )
		{
			horizontalStr[i]=board[x][i+rangeOfLeft];
		}
		//�ж���Щ�����Ƿ�����������
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

		//�����ж�
		//ȷ��������Χ
		int rangeOfHigh=x-4;
		int rangeOfLow=x+4;
		//ȷ��������Χ������
		if (rangeOfHigh<0)
		{
			rangeOfHigh=0;
		}
		if (rangeOfLow>14)
		{
			rangeOfLow=14;
		}
		//ȡ���������������
		int verticalLength=rangeOfLow-rangeOfHigh+1;
		int verticalIndex=0;//�ж�ָ��
		String[] verticalStr=new String[verticalLength];
		for (int i=0;i<verticalLength ;i++ )
		{
			verticalStr[i]=board[i+rangeOfHigh][y];
		}
		//�ж���Щ�����Ƿ�����������
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

		//�ۺ��ж��Ƿ�ֱ�߷�����Ӯ��
		if (horizontalIndex==5||verticalIndex==5)
		{
			return true;
		}
		else
			return false;
	}

	//б�߷����ж��Ƿ�Ӯ��,label�����ֲ�ͬ������ʹ�����ӵı�־
	//���ֿ���ֱ�Ӱ�һ��б���ϵ��������ݶ�ȡ����
	public boolean slashJudgment(int x,int y,String label)
	{
		//��б�����ж�
		//ȷ����б����������Χ
		int leftSlashIndex=0;
		int leftSlashLength=15-Math.abs(x-y);
		String[] leftSlashStr=new String[leftSlashLength];
		//�ҳ�x��y�������Сֵ
		int min=-1;
		if (x<y)
		{
			min=x;
		}
		else
		{
			min=y;
		}
		//ȷ����б�Ŀ�ʼ����
		int xLeftSlash=x-min;
		int yLeftSlash=y-min;
		//ȡ����б�ϵ���������
		for (int i=0;i<leftSlashLength ;i++ )
		{
			leftSlashStr[i]=board[i+xLeftSlash][i+yLeftSlash];
		}
		//�ж���б��Щ�����Ƿ�����������
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

		//��б�����ж�
		//ȷ����б������������,BOARD_SIZE=15
		int rightSlashIndex=0;
		int rightSlashLength=BOARD_SIZE-Math.abs(14-x-y);
		String[] rightSlashStr=new String[rightSlashLength];
		//ȡ����б�ϵ���������
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
		//�ж���б��Щ�����Ƿ�����������
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

		//�ۺ��ж��Ƿ�б�߷�����Ӯ��
		if (leftSlashIndex==5||rightSlashIndex==5)
		{
			return true;
		}
		else
			return false;
	}

	//������
	public static void main(String[] args)  throws Exception
	{
		Gobang gb=new Gobang();
		gb.initBoard();
		gb.printBoard();
		//��ȡ��������
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		String inputStr=null;
		System.out.println("�ֵ��û�����---------------------");
		System.out.println("����������������꣬Ӧ��x,y��ʽ��");
		while ((inputStr=br.readLine())!=null)
		{
			//�����������ָ��xy����
			String[] pasStrArr=inputStr.split(",");
			//�û����������
			int xPos=Integer.parseInt(pasStrArr[0]);
			int yPos=Integer.parseInt(pasStrArr[1]);
			//��������д��������
			int x=-1;
			int y=-1;
			int xCom=-1;
			int yCom=-1;
			//��⵱ǰ�˴��Ƿ��������
			if (gb.board[yPos-1][xPos-1]!="ʮ")
			{
				System.out.println("��������������󣬴˴��������ӣ����������룺");
				continue;
			}
			//"��"Ϊ�û���"��"Ϊ�����
			gb.board[yPos-1][xPos-1]="��";
			//ÿ���û���������´�ӡ����
			gb.printBoard();
			x=yPos-1;
			y=xPos-1;
			//��Ҫɨ������ж��Ƿ�����û�Ӯ������
			//�Ҿ��ò�����жϷ�����ÿ������һ���µ�����ʱ����������ܷ񹹳�Ӯ������ж�
			if (gb.linearJudgment(x,y,"��")||gb.slashJudgment(x,y,"��"))
			{
				System.out.println("�û�Ӯ�ˣ�");
				System.out.println("������Y/Nȷ���Ƿ��˳�������Y���˳�������N�����¿�ʼ��");
				inputStr=br.readLine();
				if (inputStr.equals("Y"))
				{
					return;
				}
				else
				{
					gb.initBoard();
					gb.printBoard();
					System.out.println("�ֵ��û�����---------------------");
					System.out.println("����������������꣬Ӧ��x,y��ʽ��");
					continue;
				}
			}
			//�˴������������������Ϊ���Ե��������꣬���귶ΧΪ[1,16)������
			System.out.println("�ֵ���������---------------------");
			int xPosCom=-1;
			int yPosCom=-1;
			//��⵱ǰ�˴��Ƿ��������
			do
			{
				xPosCom=(int)(Math.random()*15+1);
				yPosCom=(int)(Math.random()*15+1);
			}
			while (gb.board[yPosCom-1][xPosCom-1]!="ʮ");
			gb.board[yPosCom-1][xPosCom-1]="��";
			//��Ҫɨ������ж��Ƿ���ֵ���Ӯ������
			//���´�ӡ�����Լ�����
			gb.printBoard();
			xCom=yPosCom-1;
			yCom=xPosCom-1;
			if (gb.linearJudgment(xCom,yCom,"��")||gb.slashJudgment(xCom,yCom,"��"))
			{
				System.out.println("����Ӯ�ˣ�������һ�֣�");				
				gb.initBoard();
				gb.printBoard();
			}
			System.out.println("�ֵ��û�����---------------------");
			System.out.println("����������������꣬Ӧ��x,y��ʽ��");
		}
	}
}
