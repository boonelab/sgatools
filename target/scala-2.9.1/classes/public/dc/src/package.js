require("./core");

require("util").puts(JSON.stringify({
  "name": "dc",
  "version": dc.version,
  "description": "A multi-dimensional charting built to work natively with crossfilter rendered using d3.js ",
  "keywords": ["visualization", "svg", "animation", "canvas", "chart", "dimensional"],
  "homepage": "http://nickqizhu.github.com/dc.js/",
  "author": {"name": "Nick Zhu", "url": "http://nzhu.blogspot.ca/"},
  "repository": {"type": "git", "url": "https://github.com/NickQiZhu/dc.js.git"},
  "dependencies": {
    "crossfilter": "1.0.3",
    "d3": "2.9.4"
  },
  "devDependencies": {
    "uglify-js": "1.2.3",
    "vows": "0.6.x",
    "jsdom": "0.2.14",
    "jquery": "1.7.3",
    "sinon": "1.4.2"
  },
  "scripts": {"test": "./node_modules/vows/bin/vows"}
}, null, 2));