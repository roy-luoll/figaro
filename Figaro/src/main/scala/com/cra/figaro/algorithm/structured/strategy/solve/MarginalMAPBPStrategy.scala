/*
 * MarginalMAPBPStrategy.scala
 * A class that solves a marginal MAP problem using BP.
 *
 * Created By:      William Kretschmer (kretsch@mit.edu)
 * Creation Date:   July 1, 2015
 *
 * Copyright 2015 Avrom J. Pfeffer and Charles River Analytics, Inc.
 * See http://www.cra.com or email figaro@cra.com for information.
 *
 * See http://www.github.com/p2t2/figaro for a copy of the software license.
 */
package com.cra.figaro.algorithm.structured.strategy.solve

import com.cra.figaro.algorithm.structured.Problem
import com.cra.figaro.algorithm.factored.factors.Factor
import com.cra.figaro.algorithm.factored.factors.Variable
import com.cra.figaro.algorithm.structured.solver
import com.cra.figaro.algorithm.structured.NestedProblem

/**
 * A solving strategy that uses marginal BP to solve nested problems, and MPE BP
 * to solve non-nested problems.
 * @param iterations number of iterations for which to run BP for each subproblem.
 */
class MarginalMAPBPStrategy(val iterations: Int) extends SolvingStrategy {

  def solve(problem: Problem, toEliminate: Set[Variable[_]], toPreserve: Set[Variable[_]], factors: List[Factor[Double]]):
    (List[Factor[Double]], Map[Variable[_], Factor[_]]) = {
    problem match {
      case _: NestedProblem[_] => {
        solver.marginalBeliefPropagation(iterations)(problem, toEliminate, toPreserve, factors)
      }
      case _ => {
        solver.mpeBeliefPropagation(iterations)(problem, toEliminate, toPreserve, factors)
      }
    }
  }
  
}