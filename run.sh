#!/usr/bin/env bash
set -euo pipefail

ROOT="$(cd "$(dirname "$0")" && pwd)"
OUT="$ROOT/bin"
SRC="$ROOT/src"

mkdir -p "$OUT"
javac -d "$OUT" "$SRC"/*.java

cd "$ROOT"
java -cp "$OUT" LogiLatorApp
