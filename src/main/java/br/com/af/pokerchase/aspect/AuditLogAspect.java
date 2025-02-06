package br.com.af.pokerchase.aspect;

import br.com.af.pokerchase.repository.GameActionLogRepository;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuditLogAspect {

  @Autowired
  private GameActionLogRepository logRepository;

//  @AfterReturning(pointcut = "execution(* br.com.af.pokerchase.service.PokerGameService.*(..))", returning = "result")
//  public void logServiceMethod(JoinPoint joinPoint, Object result) {
//    String methodName = joinPoint.getSignature().getName();
//    String details = "Method: " + methodName + " | Result: " + result.toString();
//
//    GameActionLog log = new GameActionLog();
//    log.setActionType(ActionType.SYSTEM);
//    log.setDetails(details);
//    log.setTimestamp(Instant.now());
//
//    logRepository.save(log);
//  }
}
