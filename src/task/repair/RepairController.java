package task.repair;

public class RepairController {
  private final RepairService repairService;

  public RepairController(RepairService repairService) {
    this.repairService = repairService;
  }

  public boolean completeRepairTask(int taskId, double repairCost) {
    return repairService.completeRepairTask(taskId, repairCost);
  }
}