package server;

import java.io.Serializable;

public class Message implements Serializable{
   
	private String f="";//from
	private String t="";//to
	private String q="";//群对象名称
	private Object m="";//消息内容
	private String k="";//消息种类     sr:私人信息， gb:群发     sys系统信息   data:数据操作
	
	public String getF() {
		return f;
	}
	public void setF(String f) {
		this.f = f;
	}
	public String getT() {
		return t;
	}
	public void setT(String t) {
		this.t = t;
	}
	public String getQ() {
		return q;
	}
	public void setQ(String q) {
		this.q = q;
	}
	public Object getM() {
		return m;
	}
	public void setM(Object m) {
		this.m = m;
	}
	public String getK() {
		return k;
	}
	public void setK(String k) {
		this.k = k;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((f == null) ? 0 : f.hashCode());
		result = prime * result + ((k == null) ? 0 : k.hashCode());
		result = prime * result + ((m == null) ? 0 : m.hashCode());
		result = prime * result + ((q == null) ? 0 : q.hashCode());
		result = prime * result + ((t == null) ? 0 : t.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (f == null) {
			if (other.f != null)
				return false;
		} else if (!f.equals(other.f))
			return false;
		if (k == null) {
			if (other.k != null)
				return false;
		} else if (!k.equals(other.k))
			return false;
		if (m == null) {
			if (other.m != null)
				return false;
		} else if (!m.equals(other.m))
			return false;
		if (q == null) {
			if (other.q != null)
				return false;
		} else if (!q.equals(other.q))
			return false;
		if (t == null) {
			if (other.t != null)
				return false;
		} else if (!t.equals(other.t))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "{'f':"+"'" + f+"'" + ", 't':" +"'"+ t+"'" + ", 'q':" +"'"+q+"'" + ", 'm':"+"'" + m+"'" + ", 'k':"+"'" + k+"'" + "}";
	}

	
}
