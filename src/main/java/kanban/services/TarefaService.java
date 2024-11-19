package kanban.services;
import kanban.model.Tarefa;
import kanban.model.Tarefa.Status;
import kanban.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TarefaService {

    @Autowired
    public TarefaRepository tarefaRepository;

    public Tarefa salvarTarefa(Tarefa tarefa) {
        return tarefaRepository.save(tarefa);
    }

    public List<Tarefa> listarTarefas() {
        return tarefaRepository.findAll();
    }

    public Tarefa buscarTarefaPorId(Long id) {
        return tarefaRepository.findById(id).orElse(null);
    }

    public void excluirTarefa(Long id) {
        tarefaRepository.deleteById(id);
    }

    public List<Tarefa> listarTarefasPorStatusOrdenado(Status status) {
        return tarefaRepository.findAll().stream()
                .filter(tarefa -> tarefa.getStatus() == status)
                .sorted((t1, t2) -> t2.getPrioridade().compareTo(t1.getPrioridade()))
                .collect(Collectors.toList());
    }

    public List<Tarefa> filtrarTarefas(Tarefa.Prioridade prioridade, LocalDate dataLimite) {
        return tarefaRepository.findAll().stream()
                .filter(tarefa -> (prioridade == null || tarefa.getPrioridade() == prioridade) &&
                        (dataLimite == null || (tarefa.getDataLimite() != null && tarefa.getDataLimite().isEqual(dataLimite))))
                .collect(Collectors.toList());
    }

    public List<Tarefa> listarTarefasAtrasadas() {
        return tarefaRepository.findAll().stream()
                .filter(tarefa -> tarefa.getDataLimite() != null &&
                        tarefa.getDataLimite().isBefore(LocalDate.now()) &&
                        tarefa.getStatus() != Status.CONCLUIDO)
                .collect(Collectors.toList());
    }
}
