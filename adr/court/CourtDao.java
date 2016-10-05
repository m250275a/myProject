package adr.court;

import java.util.List;

public interface CourtDao {
	int insert(CourtVO court, byte[] image);

	int update(CourtVO court, byte[] image);

	int delete(Integer courtno);

	CourtVO findByCourtno(Integer courtno);

	List<CourtVO> getAll();

	byte[] getImage(Integer courtno);
}
