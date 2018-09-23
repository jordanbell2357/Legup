package puzzle.treetent.rules;

import model.rules.ContradictionRule;
import model.tree.TreeTransition;
import puzzle.treetent.TreeTentBoard;
import puzzle.treetent.TreeTentCell;
import puzzle.treetent.TreeTentType;

import java.awt.*;
import java.util.ArrayList;

public class NoTentForTreeContradictionRule extends ContradictionRule
{

    public NoTentForTreeContradictionRule()
    {
        super("No Tents For Tree", "Each tree must link to a tent.", "images/treetent/contra_NoTentForTree.png");
    }

    /**
     * Checks whether the transition has a contradiction at the specific element index using this rule
     *
     * @param transition   transition to check contradiction
     * @param elementIndex index of the element
     *
     * @return null if the transition contains a contradiction at the specified element,
     * otherwise error message
     */
    @Override
    public String checkContradictionAt(TreeTransition transition, int elementIndex)
    {
        TreeTentBoard board = (TreeTentBoard) transition.getBoard();
        TreeTentCell cell = (TreeTentCell)board.getElementData(elementIndex);
        return(cell.getType() != TreeTentType.TREE || cell.isLinked() || checkTentforTree(board,cell.getLocation()))?"Does not contain a contradiction": null;
        //return(cell.getType() != TreeTentType.TREE || cell.isLinked())?"Does not contain a contradiction":checkTentforTree(board,cell.getLocation())?"Does not contain a contradiction" : null;
    }

    /**
     * Checks whether the child node logically follows from the parent node using this rule
     * and if so will perform the default application of the rule
     *
     * @param transition transition to apply default application
     *
     * @return true if the child node logically follow from the parent node and accepts the changes
     * to the board, otherwise false
     */
    @Override
    public boolean doDefaultApplication(TreeTransition transition)
    {
        return false;
    }

    /**
     * Checks whether the child node logically follows from the parent node at the
     * specific element index using this rule and if so will perform the default application of the rule
     *
     * @param transition   transition to apply default application
     * @param elementIndex
     *
     * @return true if the child node logically follow from the parent node and accepts the changes
     * to the board, otherwise false
     */
    @Override
    public boolean doDefaultApplicationAt(TreeTransition transition, int elementIndex)
    {
        return false;
    }

    public boolean checkTentforTree(TreeTentBoard board, Point loc){
        ArrayList<TreeTentCell> adjacent = new ArrayList<>();
        adjacent.add(board.getCell(loc.x+1,loc.y));
        adjacent.add(board.getCell(loc.x-1,loc.y));
        adjacent.add(board.getCell(loc.x,loc.y+1));
        adjacent.add(board.getCell(loc.x,loc.y-1));
        for(TreeTentCell cell: adjacent){
            if (cell != null && (cell.getType() == TreeTentType.UNKNOWN || (cell.getType() == TreeTentType.TENT && !cell.isLinked()))) {
                return true;
            }
        }
        System.out.println("Contradiction: No tent for tree");
        return false;
    }
}