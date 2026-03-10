package de.netschach.chess2;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class MoveAttributes {
    @NonNull
    private String orig;
    @NonNull
    private Color color;
    private Class<? extends Piece> pieceType;

    @Builder.Default
    private MoveType moveType = MoveType.DEFAULT;
    private Character fromX;
    private Integer fromY;

    @NonNull
    private Character toX;
    private Integer toY;
    private Class<? extends Piece> swapPieceType;
    private Boolean capturing;
    private Boolean check;
    private Boolean checkMate;
    private int moveIndex;
}
