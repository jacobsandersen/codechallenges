package dev.jacobandersen.codechallenges.challenge.adventofcode.year2024.day9;

import dev.jacobandersen.codechallenges.challenge.adventofcode.Day;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Day9 extends Day {
    public Day9() {
        super(2024, 9, "Disk Fragmenter");
    }

    public String loadInput() {
        final StringBuilder rawFs = new StringBuilder(getInputLinesStreamNoBlanks().collect(Collectors.joining("")));

        if (rawFs.length() % 2 != 0) {
            rawFs.append("0");
        }

        return rawFs.toString();
    }

    public List<String> getExpandedFsBlockMap() {
        final String input = loadInput();
        final List<String> expandedFs = new ArrayList<>();

        for (int fileId = 0, i = 0; i < input.length(); fileId++, i += 2) {
            int blockSize = Integer.parseInt(String.valueOf(input.charAt(i)));
            for (int j = 0; j < blockSize; j++) {
                expandedFs.add(String.valueOf(fileId));
            }

            int freeSpace = Integer.parseInt(String.valueOf(input.charAt(i + 1)));
            for (int j = 0; j < freeSpace; j++) {
                expandedFs.add(".");
            }
        }

        return expandedFs;
    }

    public List<HDComponent> getExpandedFsMap() {
        final String input = loadInput();
        final List<HDComponent> expandedFs = new ArrayList<>();

        for (int fileId = 0, i = 0; i < input.length(); fileId++, i += 2) {
            List<String> blocks = new ArrayList<>();
            List<String> freeSpaces = new ArrayList<>();

            int blockSize = Integer.parseInt(String.valueOf(input.charAt(i)));
            for (int j = 0; j < blockSize; j++) {
                blocks.add(String.valueOf(fileId));
            }

            expandedFs.add(new Block(blocks, fileId));

            int freeSpace = Integer.parseInt(String.valueOf(input.charAt(i + 1)));
            if (freeSpace > 0) {
                for (int j = 0; j < freeSpace; j++) {
                    freeSpaces.add(".");
                }

                expandedFs.add(new FreeSpace(freeSpaces));
            }
        }

        return expandedFs;
    }

    @Override
    public String partOne() {
        List<String> fsMap = getExpandedFsBlockMap();

        int freeSpacePtr = 0;
        int lastPopulatedIdx = fsMap.size() - 1;

        while (freeSpacePtr < fsMap.size() && !fsMap.get(freeSpacePtr).equals(".")) {
            freeSpacePtr++;
        }

        while (freeSpacePtr < lastPopulatedIdx) {
            while (lastPopulatedIdx > freeSpacePtr && fsMap.get(lastPopulatedIdx).equals(".")) {
                lastPopulatedIdx--;
            }

            if (freeSpacePtr >= lastPopulatedIdx) break;

            fsMap.set(freeSpacePtr, fsMap.get(lastPopulatedIdx));
            fsMap.set(lastPopulatedIdx, ".");

            while (freeSpacePtr < fsMap.size() && !fsMap.get(freeSpacePtr).equals(".")) {
                freeSpacePtr++;
            }
        }

        fsMap = fsMap.subList(0, freeSpacePtr);

        List<Long> fsMapLongs = fsMap.stream().mapToLong(Long::parseLong).boxed().toList();

        long checksum = 0;
        for (int i = 0; i < fsMapLongs.size(); i++) {
            checksum += i * fsMapLongs.get(i);
        }

        return String.valueOf(checksum);
    }

    @Override
    public String partTwo() {
        List<HDComponent> fsMap = getExpandedFsMap();

        long blockCount = fsMap.stream().filter(c -> c instanceof Block).count();
        int processedBlocks = 0;

        do {
            int lastPopulatedIdx = fsMap.size() - 1;
            while (lastPopulatedIdx >= 0 && !(fsMap.get(lastPopulatedIdx) instanceof Block) || (fsMap.get(lastPopulatedIdx) instanceof Block b && b.isCannotMove())) {
                lastPopulatedIdx--;
            }

            Block toMove = (Block) fsMap.get(lastPopulatedIdx);
            boolean didMove = false;
            for (int i = 0; i < lastPopulatedIdx; i++) {
                if (fsMap.get(i) instanceof FreeSpace fs) {
                    if (fs.getSize() >= toMove.getSize()) {
                        int diff = fs.getSize() - toMove.getSize();
                        fsMap.set(i, fsMap.get(lastPopulatedIdx));
                        if (diff > 0) {
                            fsMap.add(i + 1, new FreeSpace(Arrays.asList(".".repeat(diff).split(""))));
                        }
                        fsMap.set(lastPopulatedIdx + (diff > 0 ? 1 : 0), new FreeSpace(Arrays.asList(".".repeat(fs.getSize() - diff).split(""))));
                        didMove = true;
                        break;
                    }
                }
            }

            processedBlocks++;

            if (!didMove) {
                toMove.setCannotMove(true);
            }
        } while (processedBlocks < blockCount);

        List<String> flattened = fsMap.stream().flatMap(c -> c.getData().stream()).toList();

        long checksum = 0;
        for (int i = 0; i < flattened.size(); i++) {
            if (flattened.get(i).equals(".")) continue;
            checksum += i * Long.parseLong(flattened.get(i));
        }

        return String.valueOf(checksum);
    }

    public static abstract class HDComponent {
        private final List<String> data;

        public HDComponent(List<String> data) {
            this.data = data;
        }

        public List<String> getData() {
            return data;
        }

        public final int getSize() {
            return data.size();
        }
    }

    public static class FreeSpace extends HDComponent {
        public FreeSpace(List<String> data) {
            super(data);
        }

        @Override
        public String toString() {
            return "FreeSpace{" +
                    "data=" + getData() +
                    ", size=" + getSize() +
                    '}';
        }
    }

    public static class Block extends HDComponent {
        private final int fileId;
        private boolean cannotMove;

        public Block(List<String> data, int fileId) {
            super(data);
            this.fileId = fileId;
            cannotMove = false;
        }

        public int getFileId() {
            return fileId;
        }

        public boolean isCannotMove() {
            return cannotMove;
        }

        public void setCannotMove(boolean cannotMove) {
            this.cannotMove = cannotMove;
        }

        @Override
        public String toString() {
            return "Block{" +
                    "fileId=" + fileId +
                    ", data=" + getData() +
                    ", size=" + getSize() +
                    "}";
        }
    }
}
