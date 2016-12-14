package org.kdea.email;


public interface EmailDAO {
	public int insert(EmailVO vo);
	public int idcheck(String checkid);
}
