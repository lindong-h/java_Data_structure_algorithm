package node;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.NavigableMap;
import java.util.Objects;
import java.util.Stack;
import java.util.stream.Stream;
import java.util.zip.CheckedOutputStream;

/**
 * @author dong
 * @create 2021-05-08 16:40
 */
//创建一个单链表
    /*
            链表是以节点的 1 方式存储的
            每个节点包含 dat 域，next 域：指向下一个节点
            链表的各个环节不一定是连续的存储的
            链表分带头节点的链表和没有头节点的链表，
    */
public class SignalNodeTest {
    @Test
    public void test() {
        //先创建节点
        HeroNode h1 = new HeroNode(1, "H1", "H");
        HeroNode h2 = new HeroNode(2, "H2", "HH");
        HeroNode h3 = new HeroNode(3, "H3", "HHH");
        HeroNode h4 = new HeroNode(4, "H4", "HHHH");

        //创建链表
        var node = new SignalHeroNode();
        //加入 ，按照添加顺序来添加
//        node.add(h1);
//        node.add(h2);
//        node.add(h3);
//        node.add(h4);

        //打乱添加，但是不会影响顺序，重复添加也会被找出来
        node.StrongAdd(h1);
        node.StrongAdd(h4);
        node.StrongAdd(h3);
        node.StrongAdd(h2);
//        node.StrongAdd(h2);

        //显示
        node.list();

        //测试修改
        var newH4 = new HeroNode(4, "我是修改后的H4", "H");
        node.update(newH4);

        //显示
        node.list();
        System.out.println();
        //删除
        node.delete(1);
        node.delete(4);
        node.list();

        //测试单链表中有效节点的个数
        System.out.println(getNodeLength(node.getHead()));//2

        //测试 倒数的第 1 个
        HeroNode res = findReverseIndexNode(node.getHead(), 1);
        System.out.println("倒数第一个是" + res);

        //测试没有的 倒数id ,此时链表一共有有效两个节点
        HeroNode res1 = findReverseIndexNode(node.getHead(), 3);
        System.out.println("这个元素不存在，应该会返回 " + res1);

    }
    //1:获取单链表有效节点的个数 (如果是带头结点的链表，则应该不统计头节点)

    @Test
    public void test2() {
        var h1 = new HeroNode(1, "h1", "h");
        var h2 = new HeroNode(2, "h2", "hh");
        var h3 = new HeroNode(3, "h3", "hhh");
        var h4 = new HeroNode(4, "h4", "hhhh");

        var node = new SignalHeroNode();
        //按照倒序来添加进去，然后再反转为正序
        node.add(h4);
        node.add(h3);
        node.add(h2);
        node.add(h1);
        node.list();

        System.out.println();

        //逆序打印
        reversePrintNode(node.getHead());
        System.out.println();
        //逆序打印并不会将原链表反转
        node.list();

        System.out.println();

        System.out.println("反转为正常顺序后");
        reverseNode(node.getHead());
        node.list();
    }
    @Test
    public void test3(){

        var h1 = new HeroNode(1, "h1", "h");
        var h2 = new HeroNode(2, "h2", "hh");
        var h3 = new HeroNode(3, "h3", "hhh");
        var h4 = new HeroNode(4, "h4", "hhhh");

        var node = new SignalHeroNode();
        //按照倒序来添加进去，然后再反转为正序
        node.add(h4);
        node.add(h3);
        node.add(h2);
        node.add(h1);
        node.isLast();


    }


    //合并两个有序的单链表，合并之后的链表依然有序
//    public static SignalHeroNode mergeLink(SignalHeroNode n1,SignalHeroNode n2){//错误
//        if(n1==null)
//            return n2;
//        if (n2==null)
//            return n1;
//        SignalHeroNode newNode=null;
//        if(n1.id <= n2.id){
//            newNode = n1;
//            newNode.next=mergeLink(n1.next,n2);
//        }else{
//            newNode=n2;
//            newNode.next=mergeLink(n1,n2.next);
//        }
//        return newNode;
//
//    }

    /**
     * @param head 链表的头节点
     * @return 返回的是有效节点的个数
     */
    public static int getNodeLength(HeroNode head) {
        if (head.next == null) {
            return 0;
        }
        int len = 0;
        HeroNode cur = head.next;
        while (cur != null) {
            len++;
            cur = cur.next;
        }
        return len;
    }

    //2:查找单链表的倒数第 k 个节点
    //①：接收 head 头节点和 一个 index:表示倒数第 index 个节点
    //②：先求出链表的总长度(size)：使用 getNodeLength
    //③：得到 size 后，遍历 (size-index)个后，就可以找到了
    //找到返回该节点，找不到就返回 null
    public static HeroNode findReverseIndexNode(HeroNode head, int index) {
        //判断如果链表为空，返回 null;
        if (head.next == null) {
            return null;
        }
        //第一次遍历，获取有效节点数目
        int size = getNodeLength(head);
        //第二次遍历，size-index 位置，就是我们的倒数第 k 个
        //首先是 index 的校验
        if (index <= 0 || index > size) {
            return null;
        }
        //仍然定义一个辅助变量,然后循环定位到 倒数第 K 个节点
        HeroNode cur = head.next;
        for (int i = 0; i < size - index; i++) {
            cur = cur.next;
        }
        return cur;
    }

    //反转链表
    //思路：1:先定义一个节点 reverseHead = new HeadNode();
    //2: 从头到尾遍历原先的链表，每遍历一个节点，就将其取出，并放在新的链表的最前端
    //3: 原来的链表的head.next=reverseHead.next;

    public static void reverseNode(HeroNode head) {  //此方法会直接将原先的链表反转，而不是返回反转的链表
        //如果当前链表为空，或着只有一个节点，那就无需反转，直接返回
        if (head.next == null || head.next.next == null) {
            return;
        }
        // 定义辅助指针变量 ，帮助我们遍历原来的链表
        HeroNode cur = head.next;
        HeroNode next = null;//指向当前节点[cur]的下一个节点
        HeroNode reverseHead = new HeroNode(0, "", "");

        //从头到尾遍历原先的链表，每遍历一个节点，就将其取出，并放在新的链表 reverseHead 的最前端
        while (cur != null) {
            next = cur.next;//先暂时的保存当前节点的下一个节点，因为后面需要使用到
            cur.next = reverseHead.next;//将cur 的节点指向新的链表的最前端
            reverseHead.next = cur;//将 cur 连接到新的链表上，不会怎么搞都是空指针
            cur = next;//让 cur 后移
        }
        //将 head.next指向 reverseHead.next,实现单链表的反转
        head.next = reverseHead.next;
    }

    //从尾到头打印单链表
    //第一种:先反转，再遍历（这会破坏原来单链表）
    //利用 stack ，先进后出的特点可以达到目的
    public static void reversePrintNode(HeroNode head) {
        if (head.next == null) {
            return;//空链表无法打印
        }
        var stack = new Stack<HeroNode>();
        HeroNode cur = head.next;
        //将链表的所有节点压入栈
        while (cur != null) {
            stack.push(cur);//压栈
            cur = cur.next;//后移
        }
        //将栈中的节点进行打印，pop 出栈
        while (stack.size() > 0) {
            System.out.println(stack.pop());
        }

    }

}

class HeroNode {
    public int id;
    public String name;
    public String nickName;
    // next 节点：表示单链表的 头
    public HeroNode next;

    public HeroNode(int id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
    }

    //为了显示方便
    @Override
    public String toString() {
        return "HeroNode{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickName='" + nickName + '\'' +
//                ", next=" + next +//加上其的话再打印完每次到达的节点时都会将后面的节点打印上
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeroNode)) return false;
        HeroNode heroNode = (HeroNode) o;
        return id == heroNode.id &&
                Objects.equals(name, heroNode.name) &&
                Objects.equals(nickName, heroNode.nickName);
    }

}

//管理 HeroNode的类
class SignalHeroNode {


    //先初始化的头节点
    private HeroNode head = new HeroNode(0, "", "");
    private int size;

    public HeroNode getHead() {
        return head;
    }

    //添加 节点到 单向链表
    //添加为最后一个元素的操作。即此时的 add 不够智能，不能按照在指定的位置添加，只能按照添加的顺序添加
    public void add(HeroNode h) {
        //头节点不能改变，所以先找个替代一下
        HeroNode temp = head;
        //遍历链表，找到最后一个节点
        while (true) {
            //找到链表的最后，即为空的的 temp 节点
            if (temp.next == null)
                break;
            //移动操作，即如果没有找到最后，将temp后移
            //即不断的下一个节点的 next 赋给上一个的 头节点
            temp = temp.next;

        }//循环结束，找到最后一个节点
        temp.next = h;
        size++;
    }

    //按照对应编号 插入指定位置
    public void StrongAdd(HeroNode h) {
        //头节点依然是不能动的，因此我们仍然通过一个 指针来替换
        //因为是单链表，因此我们找的 temp 是位于 添加位置的前一个节点，否则是插入不了的
        HeroNode temp = head;
        boolean flag = false;// flag 标志添加的编号是否存在，默认为 false

        while (true) {
            //说明 temp 已经在链表的最后 ,即此时的链表中可能只有一个元素，这个必须先判断，不断后续的找下一个的节点的操作会因为没有
            //没有前一个节点而报空指针异常
            if (temp.next == null) {
                break;
            }
            if (temp.next.id > h.id) { //找到对应编号应所在的位置
                break;
            } else if (temp.next.id == h.id) {//说明希望添加的编号已经存在,就在 temp的后面
                size++;
                flag = true;//说明编号存在
                break;
            }
            temp = temp.next;//后移

        }
        //判断 flag 值
        if (flag) {  //说明不能添加
            System.out.println("编号" + h.id + "已存在");
        } else {
            //插入到链表中，temp的后面
            h.next = temp.next;//本身指向后面节点的，此时的 后一个节点的头节点为 temp.next,将其指向到新加的节点中
            temp.next = h;//前面节点指向本身的
        }

    }

    //修改节点的信息，根据 id 编号来修改，即 id 编号不能改
    public void update(HeroNode h) {
        //先判断是否为空
        if (h.next == null) {
            System.out.println("[LinkList is null]");
        }
        //找到需要修改的节点
        HeroNode temp = head.next;//先做替换
        boolean flag = false;
        while (true) {
            if (temp == null) {//表示到达链表的最后一个节点
                break;
            }
            if (temp.id == h.id) {//如果进入，则就找到了
                size--;
                flag = true;
                break;
            }
            temp = temp.next;
        }
        //根据 flag 判断是否找到要修改的节点
        if (flag) {
            temp.name = h.name;
            temp.nickName = h.nickName;
        } else {  //没有找到
            System.out.println("未找到此编号的节点");
        }
    }

    //删除节点
    //思路：必须找到待删除的节点的前一个节点，
    //然后使得 temp.next=temp.next.next; 就是使得 要删除节点的上一个节点的指向 指向下下一个节点
    //被删除的节点，将不会有其他引用指向，会被垃圾回收掉
    //1:head 不能动，因此我们需要一个 temp铺助节点找到待删除的前一个节点
    //2:注意是 temp.next.id 和需要删除的节点的 id 比较
    public void delete(int id) {
        HeroNode temp = head;
        boolean flag = false;//标志是否找到要删除节点的前一个结点
        while (true) {
            if (temp.next == null) {//已经到链表的最后
                break;
            }
            if (temp.next.id == id) {
                //找到了待删除的节点的前一个节点 temp
                flag = true;
                size--;
                break;
            }
            temp = temp.next;
        }
        //判断 flag
        if (flag) {//找到
            temp.next = temp.next.next;
        } else {
            System.out.println("要删除的节点不存在");
        }
    }

    //显示链表
    public void list() {
        if (head == null) {
            System.out.println("null");
            return;
        }

        //头节点不能动，因此我们需要一个辅助变量来遍历
        HeroNode temp = head.next;

        while (true) {

            //判断是否到链表的最后
            if (temp == null) {
                break;
            }

            //如果没到，退出循环，输出节点信息
            System.out.println(temp);

            //将 temp 后移
            temp = temp.next;

        }
    }


    //找到尾节点
    public void isLast(){
        HeroNode temp=head.next;
        for(int i=1;i<=size;i++){
            if(temp==null){
                break;
            }else{temp=temp.next;}
        }
        System.out.println(temp);

    }

}


