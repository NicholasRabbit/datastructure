/*
双向链表类
*/
public class DoubleLinkedList {
	//初始化头节点
	private NodeDemo head = new NodeDemo(0,"");

	
	//获取头节点
	public NodeDemo getHead(){
		return this.head;
	}
	
	/*
	一，遍历双向链表，与遍历单向链表做法一样
	*/
	public void showList(){
		//首先，判断链表是否为空
		if(head.next == null){
			System.out.println("链表为空！");
			return;
		}
		//进行遍历，首先获取第一个节点
		NodeDemo temp = head.next;
		do {
			System.out.println("NodeDemo==>" + temp);
			temp = temp.next;  //打印一个后引用后移
		}while(temp != null);
	
	}

	/*
	二，(1)添加节点，默认不按id大小添加，添加到链表末尾
	*/
	public int add(NodeDemo newNode){
		//使用临时节点，不要动head，head是找到链表的标识
		NodeDemo temp = head;  //这里head就是this.head
		int count = 0;
		while(true){
			if(temp.next == null){
				temp.next = newNode;
				newNode.pre = temp;
				count ++;
				break;
			}
			temp = temp.next;  //遍历时，不符合条件的一定要后移，切记
		}
		return count;
	}

	//(2)有序添加节点，按id从下到大排序
	public int addByOrder(NodeDemo newNode){
		
		int count = 0;
		NodeDemo temp = head;  //=this.head
		boolean flag = false;

		//第一步，检查链表是否为空，为空则直接添加，无需判断，下面轮询包含head.next=null的情况
		/* if(temp.next == null){
			temp.next = newNode;
			newNode.pre = temp;
			return count++;
		} */
		//第二步：链表不为空，比较id进行添加
		//这里条件不能设置为：while(temp.next != null),如此会导致无法判断链表最后一个节点，因为有可能最后一个节点的id比newNode的id大
		while(true){    
			//找到第一个比新节点id大的节点，设置flag为true。
			if(temp.id > newNode.id){
				flag = true;
				break;
			}else if(temp.id == newNode.id){
				//id相同则覆盖，只需要改名字即可，别的无需动
				temp.name = newNode.name;
				System.out.println("id:"+ newNode.id +"存在，已修改名字为: " + temp.name);
				return count++;
			}
			if(temp.next == null){
				break;  //遍历到最后一个节点，终端循环
			}
			temp = temp.next;
		}
		//插入新节点
		if(flag){
			newNode.pre = temp.pre;
			newNode.next = temp;
			temp.pre.next = newNode;  //注意，temp的上一个节点的next要指向newNode，否则插入失败
			temp.pre = newNode;
			count++;
		}else{
			//如果链表内节点id都比新节点小，则添加到末尾
			temp.next = newNode;
			newNode.pre = temp;
			count++;	
		}
		return count;
	
	}
	
	/*
	三，删除节点，根据id
	*/
	public int deleteNode(int id){
		NodeDemo temp = head.next;
		int count = 0;
		//判断链表是否有节点
		if(temp == null){
			System.out.println("链表为空，无法删除！");
			return count;
		} 
		do{
			if(temp == null){	//已循环到最后
				break;  
			}
			if(temp.id == id){
				temp.pre.next = temp.next;  //双向链表可以自我删除
				/*下面直接写有问题，如果删除的是最后一个节点，那么temp.next=null，temp.next.pre会报NullPointerException*/
				//temp.next.pre = temp.pre;  
				//正确写法
				if(temp.next != null){
					temp.next.pre = temp.pre;
				}
				count ++;
				break;
			}
			temp = temp.next;  //注意，指针一定要后移，否则无限循环
		}while(true);

		if(count == 0){
			System.out.println("无此节点：" + id);
		} 
		return count;
	}

	//四，修改节点
	public boolean updateNode(NodeDemo nodeDemo){
		NodeDemo temp = head.next;
		boolean flag = false;
		if(temp == null){
			System.out.println("链表为空");
			return flag;
		}
		while(true){
			if(temp.id == nodeDemo.id){
				flag = true;
				break;
			}
			temp = temp.next;
		}
		//判断是否修改成功
		if(flag){
			temp.name = nodeDemo.name;  //节点中只有name是可修改的，其它的不用改
			System.out.println("修改成功");
		}else{
			System.out.println("无此节点");
		}
		return flag;

	}

}


//节点
class NodeDemo {

	int id;
	String name;
	NodeDemo pre;
	NodeDemo next;

	public NodeDemo(){
	
	}
	public NodeDemo(int id,String name){
		this.id = id;
		this.name = name;
	}

	public String toString(){
		return "NodeDemo==>" + "{id:" + this.id + "," + "name:" + this.name + "}";
	}


}