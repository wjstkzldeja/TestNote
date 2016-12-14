package org.kdea.uploadMyBatis;

import java.util.List;

public interface PdsDAO2 {
	
	public List<PdsVO2> list(PdsPageVO pds);
    public PdsVO2 info(int upNum);
	public int insert(PdsVO2 pds);
	public int upload(PdsVO2 pds);
	public PdsVO2 download(int upNum);
	public int delete(int upNum);
}
