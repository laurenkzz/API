package kanban.controller;
import kanban.model.Tarefa;
import kanban.model.Tarefa.Status;
import kanban.services.TarefaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/tarefas")
@RestController
public class TarefaController {

    @Autowired
    private TarefaService tarefaService;

    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        tarefa.setStatus(Status.A_FAZER);
        Tarefa novaTarefa = tarefaService.salvarTarefa(tarefa);
        return ResponseEntity.ok(novaTarefa);
    }

    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTarefas() {
        return ResponseEntity.ok(tarefaService.listarTarefas());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirTarefa(@PathVariable Long id) {
        tarefaService.excluirTarefa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<Tarefa>> listarTarefasPorStatus(@PathVariable Status status) {
        return ResponseEntity.ok(tarefaService.listarTarefasPorStatusOrdenado(status));
    }

    @GetMapping("/atrasadas")
    public ResponseEntity<List<Tarefa>> listarTarefasAtrasadas() {
        return ResponseEntity.ok(tarefaService.listarTarefasAtrasadas());
    }
}
