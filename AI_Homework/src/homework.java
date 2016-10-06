import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;
class Node
{
	String state;
	Node parentStr;
	int cost;
	int sundayCost;
	int depth;
	int totalCost;
	Node parent1;
	ArrayList<String> parent=null;
	ArrayList<Node> children=null;
	Node(String state)
	{
		this.state=state;
		parent=new ArrayList<String>();
		children=new ArrayList<Node>();
	}

	public String getState() {
		return state;
	}
	public ArrayList<Node> getChildren() {
		return children;
	}

	public ArrayList<String> getParent()
	{
		return parent;
	}
	public void setNodes(ArrayList<Node> nodes) {
		this.children = nodes;
	}
	public int getCost()
	{
		return cost;
	}
	
	
}
class NodeComparator implements Comparator<Node>
{
	
	@Override
	public int compare(Node o1, Node o2) {
		// TODO Auto-generated method stub
		if(o1.getCost()<o2.getCost())
		{
			return -1;
		}
		else if(o1.getCost()>o2.getCost())
		{
			return 1;
		}
		else 
		{	
			return 0;
		}
	}
	
}
class AStarComparator implements Comparator<Node>
{
	
	HashMap<String,Node> h1=new HashMap<String,Node>();
	public AStarComparator(HashMap<String,Node> hashMap)
	{
		h1=hashMap;
	}
	@Override
	public int compare(Node o1, Node o2) {
		// TODO Auto-generated method stub
		
		if((o1.cost+o1.sundayCost)<(o2.cost+o2.sundayCost))
		{
//			if(h1.get(o1.state).sundayCost>o1.cost||h1.get(o2.state).sundayCost>o2.cost)
//			{
//				if(o1.getCost()<o2.getCost())
//				{
//					return -1;
//				}
//				else if(o1.getCost()>o2.getCost())
//				{
//					return 1;
//				}
//				else 
//				{	
//					return 0;
//				}
//			}
			return -1;
			
		}
		if((o1.cost+o1.sundayCost)>(o2.cost+o2.sundayCost))
		{
//			if(h1.get(o1.state).sundayCost>o1.cost||h1.get(o2.state).sundayCost>o2.cost)
//			{
//				if(o1.getCost()<o2.getCost())
//				{
//					return -1;
//				}
//				else if(o1.getCost()>o2.getCost())
//				{
//					return 1;
//				}
//				else 
//				{	
//					return 0;
//				}
//			}
			return 1;
		}
		else
		{
//			if(h1.get(o1.state).sundayCost>o1.cost||h1.get(o2.state).sundayCost>o2.cost)
//			{
//				if(o1.getCost()<o2.getCost())
//				{
//					return -1;
//				}
//				else if(o1.getCost()>o2.getCost())
//				{
//					return 1;
//				}
//				else 
//				{	
//					return 0;
//				}
//			}
			return 0;
		}
	}
	
}

class BFS
{
	HashMap<String,Node> h1;
	HashMap<String,Node> children;
	HashMap<String,Node> pathBFS=new HashMap<String,Node>();
	BFS(HashMap<String,Node> hm,HashMap<String,Node> children)
	{
		h1=hm;
		this.children=children;
	}
	void searchBFS(String startState,String goalState) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		HashMap<String,Integer> hm=new HashMap<String,Integer>();
		int depth=0;
		Queue<String> q1=new LinkedList<String>();
		q1.add(startState);
		hm.put(startState, 1);
		boolean checkGoalState=false;
		while(!q1.isEmpty()&&!checkGoalState)
		{
			String elem=q1.poll();
			pathBFS.put(elem,h1.get(elem));
			if(elem.contentEquals(goalState))
			{
				break;	
			}
			depth++;
			ArrayList<Node> elements = null;
			if(h1.get(elem)!=null)
			{
				
				elements=h1.get(elem).getChildren();
				
			}
			if(elements!=null)
			{	
				Iterator<Node> itr=elements.iterator();
				while(itr.hasNext())
				{
					String next=itr.next().state;
					if(hm.get(next)==null)
					{
						h1.get(next).parentStr=h1.get(elem);
						if(next.contentEquals(goalState))
						{
							System.out.println("Inside here");
							
							pathBFS.put(next, h1.get(next));
							checkGoalState=true;
							break;
						}
						hm.put(next, 1);
						
						q1.add(next);
					}
				}
			}	
		}
		
		Stack<String> fullPath=new Stack<String>();
		for(String a:pathBFS.keySet())
		{
			if(pathBFS.get(a).parentStr!=null)
				System.out.println("State "+a+" path--> "+pathBFS.get(a).parentStr.state);
		}
		String sampleString=goalState;
		while(!sampleString.contentEquals(startState))
		{
			fullPath.push(sampleString);
			sampleString=pathBFS.get(goalState).parentStr.state;
			goalState=sampleString;
		}
		
		fullPath.push(startState);
		depth=0;
		while(!fullPath.isEmpty())
		{
			String elem=fullPath.pop();
			writer.write(elem+" "+depth+"\n");
			System.out.println(elem+" "+depth);
			depth++;
		}
		writer.close();
	}
	
}
class DFS
{

	HashMap<String,Node> h1;
	HashMap<String,Node> children;
	ArrayList<String> pathDFS=new ArrayList<String>();
	DFS(HashMap<String,Node> hm,HashMap<String,Node> children)
	{
		h1=hm;
		this.children=children;
	}
	
	void searchDFS(String startState,String goalState) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		HashMap<String,Integer> hm=new HashMap<String,Integer>();
		int depth=0;
		Stack<String> s1=new Stack<String>();
		s1.push(startState);
		hm.put(startState, 1);
		boolean checkGoalState=false;
		while(!s1.isEmpty()&&!checkGoalState)
		{
			String elem=s1.pop();
			if(elem.contentEquals(goalState))
			{
				System.out.println(elem+" "+depth);
				break;	
			}
			System.out.println(elem+" "+depth);
			pathDFS.add(elem);
			depth++;
			ArrayList<Node> elements = null;
			if(h1.get(elem)!=null)
			{
				elements=h1.get(elem).getChildren();
			}
			if(elements!=null)	
			{
				int j=elements.size()-1;
				String next="";
				while(j>=0)
				{
					next=elements.get(j).state;
					j--;
					if(hm.get(next)==null)
					{
						if(next.contentEquals(goalState))
						{
							System.out.println(next+" "+depth);
							checkGoalState=true;
							pathDFS.add(next);
							break;
						}
						hm.put(next, 1);
						s1.push(next);
					}
					
				}
				
			}	
		}
		System.out.println("----");
		
		Stack<String> path=new Stack<String>();
		int pathIndex=pathDFS.size()-1;
		path.push(pathDFS.get(pathIndex));
		pathIndex--;
		path.push(pathDFS.get(pathIndex));
		if(children.get(pathDFS.get(pathIndex))!=null&&pathIndex>0)
		{	
			
			ArrayList<String> parent=children.get(pathDFS.get(pathIndex)).parent;
			int parentIndex=parent.size()-1;
			boolean reachParent=false;
			pathIndex--;
			while(!reachParent)
			{
				boolean exit=false;
				String element=null;
				while(!exit&&parentIndex>=0)
				{	
					String p=parent.get(parentIndex);
					parentIndex--;
					for(int i=pathIndex;i>=0;i--)
					{
						if(pathDFS.get(i).contentEquals(p))
						{
							if(p.contentEquals(startState))
							{
								path.push(p);
								reachParent=true;
								exit=true;
								break;
							}
							else
							{
								exit=true;
								element=p;
								path.push(p);
								pathIndex=i-1;
							}
								
						}
					}
				}
				if(element!=null)
					parent=children.get(element).getParent();
				parentIndex=parent.size()-1;
				
			}
		}
		depth=0;
		while(!path.isEmpty())
		{
			String elem=path.pop();
			writer.println(elem+" "+depth);
			System.out.println(elem+" "+depth);
			depth++;
		}
		
		writer.close();
	}
	
}
class UCS
{
	HashMap<String,ArrayList<String>> childCopy=new HashMap<String,ArrayList<String>>();
	HashMap<String,Node> h1;
	HashMap<String,Node> children;
	HashMap<String,ArrayList<String>> edgeCost;
	HashMap<String,Node> path=new HashMap<String,Node>();
	HashMap<String,Node> parent=new HashMap<String,Node>();
	
	
	UCS(HashMap<String,Node> hm,HashMap<String,ArrayList<String>> edgeCost,HashMap<String,Node> children
			,HashMap<String,Node> parent)
	{
		h1=hm;
		this.edgeCost=edgeCost;
		this.children=children;
		this.parent=parent;
	}
	void searchUCS(String startState,String goalState) throws FileNotFoundException, UnsupportedEncodingException
	{
		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		Comparator<Node> comp=new NodeComparator();
		PriorityQueue<Node> pq=new PriorityQueue<Node>(comp);
		Iterator<String> itr=h1.keySet().iterator();
		//Adding all the adjacent nodes of start state in priority queue
		
		Set<String> explored=new HashSet<String>();//explored set
		//Loop while pq is not empty or we have not reached a goal state
		pq.add(h1.get(startState));
		while(!pq.isEmpty())
		{
			Node node1=pq.remove();
			String state=node1.getState();
			int cost=node1.cost;
			path.put(state,node1);
			if(state.contentEquals(goalState))
			{
				break;
			}
			explored.add(state);
			Node node=h1.get(node1.state);
			if(node==null)
				continue;
			ArrayList<Node> nodes=node.getChildren();//get all adjacent nodes
			if(nodes.size()>0)
			{	
				Iterator<Node> itrNode=nodes.iterator();
				
				//check if already there in pq, if there then update the cost else add it
				while(itrNode.hasNext())
				{
					Node nextNode=itrNode.next();
					boolean inPriorityQueue=false;
				
					if(!explored.contains(nextNode.state))
					{
						Iterator<Node> pqNode=pq.iterator();
						Node updateNode=null;
						while(pqNode.hasNext())
						{
							Node pqNextNode=pqNode.next();
							if(nextNode.getState().contentEquals(pqNextNode.getState()))
							{
								
								inPriorityQueue=true;
								if((cost+nextNode.cost)<pqNextNode.cost)
								{
									pqNextNode.cost=cost+nextNode.cost;
									pqNextNode.parentStr=node1;
									updateNode=pqNextNode;
									pq.remove(pqNextNode);
									break;
								}
							}
						}
						if(updateNode!=null)
						{
							pq.add(updateNode);
						}
						if(!inPriorityQueue)
						{
							nextNode.cost=cost+nextNode.cost;
							nextNode.parentStr=node1;
							pq.add(nextNode);
						}
					}
				}
				
			}
		}
		Stack<String> fullPath=new Stack<String>();
		
		String sampleString=goalState;
		while(!sampleString.contentEquals(startState))
		{
			fullPath.push(sampleString+" "+path.get(goalState).cost);
			sampleString=path.get(goalState).parentStr.state;
			goalState=sampleString;
		}
		
		fullPath.push(startState+" "+0);
		while(!fullPath.isEmpty())
		{
			String elem=fullPath.pop();
			writer.write(elem+"\n");
			System.out.println(elem);
		}
		writer.close();
	}
	
}
class AStar
{
	HashMap<String,Node> h1;
	HashMap<String,Node> children;
	HashMap<String,ArrayList<String>> edgeCost;
	HashMap<String,Node> path=new HashMap<String,Node>();
	ArrayList<String> arrayPath=new ArrayList<String>();
	HashMap<String,Node> parent=new HashMap<String,Node>();
	homework h12;
	
	AStar(HashMap<String,Node> hm,HashMap<String,ArrayList<String>> edgeCost,HashMap<String,Node> children
			,HashMap<String,Node> parent,homework h12)
	{
		h1=hm;
		this.edgeCost=edgeCost;
		this.children=children;
		this.parent=parent;
		this.h12=h12;
	}

	

	public void searchAStar(String startState, String goalState) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		
		PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
		Comparator<Node> comp=new AStarComparator(h1);
		PriorityQueue<Node> pq=new PriorityQueue<Node>(comp);
		Iterator<String> itr=h1.keySet().iterator();
		//Adding all the adjacent nodes of start state in priority queue
		
		HashMap<String,Node> explored=new HashMap<String,Node>();//explored set
		//Loop while pq is not empty or we have not reached a goal state
		Node startNode=h1.get(startState);
		startNode.sundayCost=h1.get(startState).sundayCost;
		pq.add(startNode);
		while(!pq.isEmpty())
		{
			Node node1=pq.poll();
			String state=node1.getState();
			int cost=node1.cost;
			path.put(state,node1);
			if(state.contentEquals(goalState))
			{
				break;
			}
			explored.put(state,node1);
			Node node=h1.get(node1.state);
			if(node==null)
				continue;
			ArrayList<Node> nodes=node.getChildren();//get all adjacent nodes
			
			if(nodes.size()>0)
			{	
				Iterator<Node> itrNode=nodes.iterator();
				
				//check if already there in pq, if there then update the cost else add it
				while(itrNode.hasNext())
				{
					Node nextNode=itrNode.next();
					nextNode.sundayCost=h1.get(nextNode.state).sundayCost;
					boolean inPriorityQueue=false;
				
					if(explored.get(nextNode.state)==null)
					{
						Iterator<Node> pqNode=pq.iterator();
						Node updateNode=null;
						while(pqNode.hasNext())
						{
							Node pqNextNode=pqNode.next();
							if(nextNode.getState().contentEquals(pqNextNode.getState()))
							{
								
								inPriorityQueue=true;
								if((cost+nextNode.cost)<pqNextNode.cost)
								{
									pqNextNode.cost=cost+nextNode.cost;
									pqNextNode.parentStr=node1;
									updateNode=pqNextNode;
									updateNode.cost=cost+nextNode.cost;
									pq.remove(pqNextNode);
									break;
								}
							}
						}
						if(updateNode!=null)
						{
							pq.add(updateNode);
						}
						if(!inPriorityQueue)
						{
							nextNode.cost=cost+nextNode.cost;
							nextNode.parentStr=node1;
							pq.add(nextNode);
						}
					}
				}
				
				
			}
		}
		Stack<String> fullPath=new Stack<String>();
		
		String sampleString=goalState;
		while(!sampleString.contentEquals(startState))
		{
			fullPath.push(sampleString+" "+path.get(goalState).cost);
			sampleString=path.get(goalState).parentStr.state;
			goalState=sampleString;
		}
		
		fullPath.push(startState+" "+0);
		while(!fullPath.isEmpty())
		{
			String elem=fullPath.pop();
			writer.write(elem+"\n");
			System.out.println(elem);
		}
		writer.close();
	}
	
}
public class homework {

	private String startState;
	private String goalState;
	private int noOfLiveTrafficLines;
	private int noOfSundayLines;
	private String[] fileInfo;
	private BFS bfs=null;
	private DFS dfs=null;
	public UCS ucs=null;
	private AStar astar=null;
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException
	{
		
		homework h1=new homework();
		String info="";
		Scanner in=new Scanner(new FileReader("input_4.txt"));
		while(in.hasNext())
		{
			info=info+in.nextLine()+"\n";
		}
		in.close();
		h1.fileInfo=info.split("\n");
		String algo=h1.fileInfo[0].trim();//Algo to use
		h1.startState=h1.fileInfo[1].trim();//Start State
		h1.goalState=h1.fileInfo[2].trim();//Goal State
		h1.noOfLiveTrafficLines=Integer.parseInt(h1.fileInfo[3]);//No of LiveTrafficLines
		h1.noOfSundayLines=Integer.parseInt(h1.fileInfo[4+h1.noOfLiveTrafficLines]);
		
		if(h1.startState.contentEquals(h1.goalState))
		{
			PrintWriter writer = new PrintWriter("output.txt", "UTF-8");
			writer.println(h1.startState+" 0");
			writer.close();
		}
		else
		{	
			if(algo.contentEquals("BFS")||algo.contentEquals("DFS"))
			{
				HashMap<String,Node> hm=new HashMap<String,Node>();
				HashMap<String,Node> children=new HashMap<String,Node>();
				for(int i=0;i<h1.noOfLiveTrafficLines;i++)
				{
					String[] states=h1.fileInfo[4+i].split(" ");
					if(hm.get(states[0])!=null&&children.get(states[1])==null)
						{
							Node child=new Node(states[1]);
							child.getParent().add(states[0]);
							children.put(states[1], child);
							hm.get(states[0]).getChildren().add(child);
						}
						else if(hm.get(states[0])!=null&&children.get(states[1])!=null)
						{
							children.get(states[1]).getParent().add(states[0]);
							hm.get(states[0]).getChildren().add(children.get(states[1]));
						}
						else if(hm.get(states[0])==null&&children.get(states[1])!=null)
						{
							Node parent=new Node(states[0]);
							parent.getChildren().add(children.get(states[1]));
							hm.put(states[0], parent);
							children.get(states[1]).getParent().add(states[0]);
							
						}
						else if(hm.get(states[0])==null&&children.get(states[1])==null)
						{
							Node parent=new Node(states[0]);
							Node child=new Node(states[1]);
							child.getParent().add(states[0]);
							parent.getChildren().add(child);
							children.put(states[1], child);
							hm.put(states[0], parent);
						}
				}
				
				if(algo.contentEquals("BFS"))
				{	
					if(hm.get(h1.goalState)==null)
					{
						hm.put(h1.goalState, new Node(h1.goalState));
					}
					h1.bfs=new BFS(hm,children);
					h1.bfs.searchBFS(h1.startState,h1.goalState);
				}
				else
				{
					h1.dfs=new DFS(hm,children);
					h1.dfs.searchDFS(h1.startState,h1.goalState);
				}
			}
			else if(algo.contentEquals("UCS"))
			{
				HashMap<String,Node> nodeMap=new HashMap<String,Node>();
				HashMap<String,Integer> visited=new HashMap<String,Integer>();
				HashMap<String,Node> children=new HashMap<String,Node>();
				HashMap<String,Node> hm=new HashMap<String,Node>();
				
				HashMap<String,ArrayList<String>> sameCost=new HashMap<String,ArrayList<String>>();
				for(int i=0;i<h1.noOfLiveTrafficLines;i++)
				{
					String[] line=h1.fileInfo[4+i].split(" ");
					if(sameCost.get(line[0])==null)
					{
						ArrayList<String> elems=new ArrayList<String>();
						elems.add(line[1]);
					}
					else
					{
						ArrayList<String> al = sameCost.get(line[0]);
						al.add(line[1]);
						sameCost.put(line[0], al);
					}
					
					Node toNode=new Node(line[1]);
					toNode.cost=Integer.parseInt(line[2]);
					if(visited.containsKey(line[0])==false)
					{	
						
						ArrayList<Node> nodes=new ArrayList<Node>();
						Node fromNode=new Node(line[0]);
						nodes.add(toNode);
						fromNode.setNodes(nodes);
						visited.put(line[0],1);
						nodeMap.put(fromNode.getState(),fromNode);
					}
					else
					{
						Set<String> nodeSet=nodeMap.keySet();
						Iterator<String> itr=nodeSet.iterator();
						while(itr.hasNext())
						{
							String next=itr.next();
							if(next.contentEquals(line[0]))
							{
								Node nextNode=nodeMap.get(next);
								ArrayList<Node> nodes1=nextNode.getChildren();
								nodes1.add(toNode);
								nextNode.setNodes(nodes1);
								nodeMap.put(next, nextNode);
								break;
							}
						}
					}
					
					if(hm.get(line[0])!=null&&children.get(line[1])==null)
					{
						Node child=new Node(line[1]);
						child.getParent().add(line[0]+" "+line[2]);
						children.put(line[1], child);
						hm.get(line[0]).getChildren().add(child);
					}
					else if(hm.get(line[0])!=null&&children.get(line[1])!=null)
					{
						children.get(line[1]).getParent().add(line[0]+" "+line[2]);
						hm.get(line[0]).getChildren().add(children.get(line[1]));
					}
					else if(hm.get(line[0])==null&&children.get(line[1])!=null)
					{
						Node parent=new Node(line[0]);
						parent.getChildren().add(children.get(line[1]));
						hm.put(line[0], parent);
						children.get(line[1]).getParent().add(line[0]+" "+line[2]);
						
					}
					else if(hm.get(line[0])==null&&children.get(line[1])==null)
					{
						Node parent=new Node(line[0]);
						Node child=new Node(line[1]);
						parent.cost=Integer.parseInt(line[2]);
						child.getParent().add(line[0]+" "+line[2]);
						child.cost=Integer.parseInt(line[2]);
						parent.getChildren().add(child);
						children.put(line[1], child);
						hm.put(line[0], parent);
					}
				}
				
				h1.ucs=new UCS(nodeMap,sameCost,children,nodeMap);
				h1.ucs.searchUCS(h1.startState,h1.goalState);
				
			}
			else if(algo.contentEquals("A*"))
			{
				HashMap<String,Node> nodeMap=new HashMap<String,Node>();
				HashMap<String,Integer> visited=new HashMap<String,Integer>();
				HashMap<String,Node> children=new HashMap<String,Node>();
				HashMap<String,Node> hm=new HashMap<String,Node>();
				
				HashMap<String,ArrayList<String>> sameCost=new HashMap<String,ArrayList<String>>();
				for(int i=0;i<h1.noOfLiveTrafficLines;i++)
				{
					String[] line=h1.fileInfo[4+i].split(" ");
					if(sameCost.get(line[0])==null)
					{
						ArrayList<String> elems=new ArrayList<String>();
						elems.add(line[1]);
					}
					else
					{
						ArrayList<String> al = sameCost.get(line[0]);
						al.add(line[1]);
						sameCost.put(line[0], al);
					}
					
					Node toNode=new Node(line[1]);
					toNode.cost=Integer.parseInt(line[2]);
					if(visited.containsKey(line[0])==false)
					{	
						
						ArrayList<Node> nodes=new ArrayList<Node>();
						Node fromNode=new Node(line[0]);
						nodes.add(toNode);
						fromNode.setNodes(nodes);
						visited.put(line[0],1);
						nodeMap.put(fromNode.getState(),fromNode);
					}
					else
					{
						Set<String> nodeSet=nodeMap.keySet();
						Iterator<String> itr=nodeSet.iterator();
						while(itr.hasNext())
						{
							String next=itr.next();
							if(next.contentEquals(line[0]))
							{
								Node nextNode=nodeMap.get(next);
								ArrayList<Node> nodes1=nextNode.getChildren();
								nodes1.add(toNode);
								nextNode.setNodes(nodes1);
								nodeMap.put(next, nextNode);
								break;
							}
						}
					}
					
					if(hm.get(line[0])!=null&&children.get(line[1])==null)
					{
						Node child=new Node(line[1]);
						child.getParent().add(line[0]+" "+line[2]);
						children.put(line[1], child);
						hm.get(line[0]).getChildren().add(child);
					}
					else if(hm.get(line[0])!=null&&children.get(line[1])!=null)
					{
						children.get(line[1]).getParent().add(line[0]+" "+line[2]);
						hm.get(line[0]).getChildren().add(children.get(line[1]));
					}
					else if(hm.get(line[0])==null&&children.get(line[1])!=null)
					{
						Node parent=new Node(line[0]);
						parent.getChildren().add(children.get(line[1]));
						hm.put(line[0], parent);
						children.get(line[1]).getParent().add(line[0]+" "+line[2]);
						
					}
					else if(hm.get(line[0])==null&&children.get(line[1])==null)
					{
						Node parent=new Node(line[0]);
						Node child=new Node(line[1]);
						parent.cost=Integer.parseInt(line[2]);
						child.getParent().add(line[0]+" "+line[2]);
						child.cost=Integer.parseInt(line[2]);
						parent.getChildren().add(child);
						children.put(line[1], child);
						hm.put(line[0], parent);
					}
				}
				int ind=5+h1.noOfLiveTrafficLines;
				for(int i=0;i<h1.noOfSundayLines;i++)
				{
					String[] line=h1.fileInfo[ind+i].split(" ");
					if(nodeMap.get(line[0])==null)
					{
						Node newNode=new Node(line[0]);
						newNode.sundayCost=Integer.parseInt(line[1]);
						nodeMap.put(line[0], newNode);
					}
					else
					{
						nodeMap.get(line[0]).sundayCost=Integer.parseInt(line[1]);
					}
				}
				h1.astar=new AStar(nodeMap,sameCost,children,hm,h1);
				h1.astar.searchAStar(h1.startState,h1.goalState);
				
			}
			
		}	
	}
	
}

