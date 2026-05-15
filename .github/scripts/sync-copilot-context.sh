#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(git rev-parse --show-toplevel 2>/dev/null || pwd)"
CONTEXT_DIR="$ROOT_DIR/.copilot-context"
GITHUB_DIR="$ROOT_DIR/.github"
OUTPUT_FILE="$GITHUB_DIR/copilot-instructions.md"
BACKUP_FILE="$GITHUB_DIR/copilot-instructions.md.bak"
TEMPLATE_FILE="$GITHUB_DIR/copilot-instructions.template.md"

ARQ_FILE="$CONTEXT_DIR/arquitectura.md"
PAT_FILE="$CONTEXT_DIR/patrones.md"
COMMIT_FILE="$GITHUB_DIR/git-commit-instructions.md"

mkdir -p "$GITHUB_DIR"

if [[ ! -f "$ARQ_FILE" ]]; then
  echo "ERROR: missing $ARQ_FILE"
  exit 1
fi

if [[ ! -f "$PAT_FILE" ]]; then
  echo "ERROR: missing $PAT_FILE"
  exit 1
fi

if [[ ! -f "$TEMPLATE_FILE" ]]; then
  echo "ERROR: missing $TEMPLATE_FILE"
  exit 1
fi

if [[ -f "$OUTPUT_FILE" ]]; then
  cp "$OUTPUT_FILE" "$BACKUP_FILE"
fi

cat "$TEMPLATE_FILE" > "$OUTPUT_FILE"

{
  echo
  echo "---"
  echo
  echo "## Extended architecture context"
  echo
  echo "The following sections are synchronized from repository context files so Copilot can read additional project-specific details."
  echo
  echo
  echo "### From .copilot-context/arquitectura.md"
  echo
  sed '1d' "$ARQ_FILE"
  echo
  echo "---"
  echo
  echo "### From .copilot-context/patrones.md"
  echo
  sed '1d' "$PAT_FILE"
} >> "$OUTPUT_FILE"

if [[ -f "$COMMIT_FILE" ]]; then
  {
    echo
    echo "---"
    echo
    echo "## Commit workflow reference"
    echo
    cat "$COMMIT_FILE"
  } >> "$OUTPUT_FILE"
fi

echo "Generated $OUTPUT_FILE"
if [[ -f "$BACKUP_FILE" ]]; then
  echo "Backup saved to $BACKUP_FILE"
fi
