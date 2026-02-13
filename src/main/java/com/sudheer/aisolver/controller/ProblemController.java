//package com.sudheer.aisolver.controller;
//
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//
//import com.sudheer.aisolver.dto.ProblemRequest;
//import com.sudheer.aisolver.service.ProblemService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//@CrossOrigin
//public class ProblemController {
//
//    private final ProblemService problemService;
//
//    @PostMapping("/solve")
//    public ResponseEntity<String> solve(@RequestBody ProblemRequest request) {
//        String result = problemService.solveProblem(request);
//        return ResponseEntity.ok()
//                .contentType(MediaType.APPLICATION_JSON)
//                .body(result);
//    }
//
//}

//package com.sudheer.aisolver.controller;
//
//import com.sudheer.aisolver.dto.ProblemRequest;
//import com.sudheer.aisolver.service.ProblemService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class ProblemController {
//
//    private final ProblemService problemService;
//
//    @PostMapping("/solve")
//    public String solve(@RequestBody ProblemRequest request) {
//        return problemService.solveProblem(request);
//    }
//}

//package com.sudheer.aisolver.controller;
//
//import com.sudheer.aisolver.dto.AiResponse;
//import com.sudheer.aisolver.dto.ProblemRequest;
//import com.sudheer.aisolver.service.ProblemService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Mono;
//
//@RestController
//@RequestMapping("/api")
//@RequiredArgsConstructor
//public class ProblemController {
//
//    private final ProblemService problemService;
//
//    @PostMapping("/solve")
//    public Mono<AiResponse> solve(@RequestBody ProblemRequest request) {
//        return problemService.solveProblem(request);
//    }
//}

package com.sudheer.aisolver.controller;

import com.sudheer.aisolver.dto.AiResponse;
import com.sudheer.aisolver.dto.ProblemRequest;
import com.sudheer.aisolver.entity.AiProblem;
import com.sudheer.aisolver.repository.AiProblemRepository;
import com.sudheer.aisolver.service.ProblemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProblemController {

    private final ProblemService problemService;
    private final AiProblemRepository repository;

    @PostMapping("/solve")
    public Mono<AiResponse> solve(@RequestBody ProblemRequest request) {
        return problemService.solveProblem(request);
    }

    //  HISTORY API
    @GetMapping("/history")
    public List<AiProblem> getHistory() {
        return repository.findAll();
    }
}
