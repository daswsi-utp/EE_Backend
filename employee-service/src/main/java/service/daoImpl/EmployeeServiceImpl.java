package service.daoImpl;

import dto.request.EmployeeRequestDTO;
import dto.response.EmployeeResponseDTO;
import model.Employee;
import repository.EmployeeRepository;
import service.dao.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    @Override
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        Employee employee = toEntity(dto);
        return toResponseDTO(repository.save(employee));
    }

    @Override
    public EmployeeResponseDTO getEmployeeById(Long id) {
        return repository.findById(id)
                .map(this::toResponseDTO)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));
    }

    @Override
    public List<EmployeeResponseDTO> getAllEmployees() {
        return repository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado"));

        employee.setFirstName(dto.getFirstName());
        employee.setLastName(dto.getLastName());
        employee.setEmail(dto.getEmail());
        employee.setPosition(dto.getPosition());

        return toResponseDTO(repository.save(employee));
    }

    @Override
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    private Employee toEntity(EmployeeRequestDTO dto) {
        Employee e = new Employee();
        e.setFirstName(dto.getFirstName());
        e.setLastName(dto.getLastName());
        e.setEmail(dto.getEmail());
        e.setPosition(dto.getPosition());
        return e;
    }

    private EmployeeResponseDTO toResponseDTO(Employee e) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(e.getId());
        dto.setFirstName(e.getFirstName());
        dto.setLastName(e.getLastName());
        dto.setEmail(e.getEmail());
        dto.setPosition(e.getPosition());
        return dto;
    }
}
