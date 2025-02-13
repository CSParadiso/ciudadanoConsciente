---

excalidraw-plugin: parsed
tags: [excalidraw]

---
==⚠  Switch to EXCALIDRAW VIEW in the MORE OPTIONS menu of this document. ⚠==



# Code Block

```<?xml version="1.0" encoding="UTF-8"?>
<mxfile host="app.diagrams.net" agent="Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:135.0) Gecko/20100101 Firefox/135.0" version="26.0.11">
  <diagram name="Page-1" id="B3IvyyJTzz6X2-31m1zG">
    <mxGraphModel dx="245" dy="3003" grid="1" gridSize="10" guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="1" pageScale="1" pageWidth="827" pageHeight="1169" math="0" shadow="0">
      <root>
        <mxCell id="0" />
        <mxCell id="1" parent="0" />
        <mxCell id="2" value="Organization" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1">
          <mxGeometry x="3460" y="-210" width="160" height="120" as="geometry" />
        </mxCell>
        <mxCell id="3" value="&lt;b&gt;+ id&lt;/b&gt;&lt;br&gt;+ name&lt;br&gt;+ email&lt;br&gt;+ description" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="2">
          <mxGeometry y="50" width="160" height="70" as="geometry" />
        </mxCell>
        <mxCell id="4" value="Reference" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="2510" y="-190" width="160" height="139" as="geometry" />
        </mxCell>
        <mxCell id="5" value="&lt;b&gt;+ id&lt;/b&gt;&lt;br&gt;+ url&amp;nbsp;&lt;br&gt;+ description&lt;br&gt;+ level&lt;br&gt;+ title" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="4">
          <mxGeometry y="50" width="160" height="89" as="geometry" />
        </mxCell>
        <mxCell id="6" value="[1, 1]" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0;exitY=1;exitDx=0;exitDy=0;endArrow=none;endFill=0;fontSize=20;entryX=1;entryY=0.5;entryDx=0;entryDy=0;" edge="1" source="3" target="12" parent="1">
          <mxGeometry x="0.6471" relative="1" as="geometry">
            <mxPoint as="offset" />
            <mxPoint x="3150" y="-638" as="sourcePoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="7" value="[ 0, M ]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="6">
          <mxGeometry x="-0.5901" y="4" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="8" value="ActivityType" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="3998" y="-752" width="170" height="118" as="geometry" />
        </mxCell>
        <mxCell id="9" value="&lt;b&gt;+ id&lt;br&gt;+ name&lt;br&gt;&lt;/b&gt;+ description&lt;br&gt;+ creator" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="8">
          <mxGeometry y="50" width="170" height="68" as="geometry" />
        </mxCell>
        <mxCell id="10" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.75;exitY=0;exitDx=0;exitDy=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;endArrow=none;endFill=0;edgeStyle=orthogonalEdgeStyle;" edge="1" source="11" target="53" parent="1">
          <mxGeometry relative="1" as="geometry">
            <Array as="points">
              <mxPoint x="2950" y="-310" />
              <mxPoint x="3190" y="-310" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="11" value="Level" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;direction=east;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1">
          <mxGeometry x="2830" y="-190" width="160" height="149" as="geometry">
            <mxRectangle x="290" y="210" width="80" height="50" as="alternateBounds" />
          </mxGeometry>
        </mxCell>
        <mxCell id="12" value="+ &lt;b&gt;id&lt;br&gt;&lt;/b&gt;+ name&lt;br&gt;+ description&lt;br&gt;+ organization&lt;br&gt;+ parent" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="11">
          <mxGeometry y="50" width="160" height="99" as="geometry" />
        </mxCell>
        <mxCell id="13" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0.25;exitDx=0;exitDy=0;entryX=1;entryY=0.25;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" parent="11" source="11" target="12">
          <mxGeometry relative="1" as="geometry">
            <Array as="points">
              <mxPoint x="260" y="37" />
              <mxPoint x="260" y="75" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="14" value="[0, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="13">
          <mxGeometry x="-0.6448" y="4" relative="1" as="geometry">
            <mxPoint y="4" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="15" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0.25;exitDx=0;exitDy=0;endArrow=none;endFill=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;" edge="1" source="19" target="8" parent="1">
          <mxGeometry relative="1" as="geometry">
            <Array as="points">
              <mxPoint x="4080" y="-1030" />
              <mxPoint x="4080" y="-752" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="16" value="[ 0, M ]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="15">
          <mxGeometry x="-0.9621" y="-2" relative="1" as="geometry">
            <mxPoint x="25" y="18" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="17" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;[ 1, 1 ]&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="15">
          <mxGeometry x="0.9687" y="-2" relative="1" as="geometry">
            <mxPoint x="-38" y="-2" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="18" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;create&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="15">
          <mxGeometry x="-0.0003" y="3" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="19" value="User" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#e3c800;fontColor=#000000;strokeColor=#B09500;" vertex="1" parent="1">
          <mxGeometry x="2990" y="-1060" width="160" height="120" as="geometry" />
        </mxCell>
        <mxCell id="20" value="+ &lt;b&gt;id&lt;/b&gt;&lt;br&gt;+ email&lt;br&gt;+ username&lt;br&gt;+ password" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="19">
          <mxGeometry y="50" width="160" height="70" as="geometry" />
        </mxCell>
        <mxCell id="21" value="Activity" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="4720" y="-162" width="160" height="147.5" as="geometry" />
        </mxCell>
        <mxCell id="22" value="&lt;b&gt;+ id&lt;/b&gt;&lt;div&gt;+ content&amp;nbsp;&lt;br&gt;+ description&lt;br&gt;+ level&lt;/div&gt;" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="21">
          <mxGeometry y="50" width="160" height="97.5" as="geometry" />
        </mxCell>
        <mxCell id="23" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;endArrow=none;endFill=0;entryX=0.25;entryY=0;entryDx=0;entryDy=0;exitX=1;exitY=0;exitDx=0;exitDy=0;" edge="1" source="80" target="21" parent="1">
          <mxGeometry relative="1" as="geometry">
            <mxPoint x="5180" y="-103.5" as="sourcePoint" />
            <Array as="points">
              <mxPoint x="4760" y="-210" />
            </Array>
            <mxPoint x="4350" y="-100" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="24" value="[1,1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="23">
          <mxGeometry x="0.5886" y="-2" relative="1" as="geometry">
            <mxPoint x="-28" y="8" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="25" value="[1, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="23">
          <mxGeometry x="-0.5306" y="2" relative="1" as="geometry">
            <mxPoint x="-4" y="-24" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="26" value="Tag" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="2790" y="-470" width="160" height="120" as="geometry" />
        </mxCell>
        <mxCell id="27" value="&lt;b&gt;+id&lt;/b&gt;&lt;br&gt;+name" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="26">
          <mxGeometry y="50" width="160" height="70" as="geometry" />
        </mxCell>
        <mxCell id="28" value="&lt;span style=&quot;color: rgb(0, 0, 0); font-family: Helvetica; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: center; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;&quot;&gt;[0, M]&lt;/span&gt;" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.5;exitY=1;exitDx=0;exitDy=0;endArrow=none;endFill=0;entryX=0.25;entryY=0;entryDx=0;entryDy=0;" edge="1" source="27" target="11" parent="1">
          <mxGeometry x="0.625" y="110" relative="1" as="geometry">
            <mxPoint x="2910" y="-270" as="sourcePoint" />
            <mxPoint x="2964.8275862068967" y="-65" as="targetPoint" />
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="29" value="[1, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="28">
          <mxGeometry x="-0.6" relative="1" as="geometry">
            <mxPoint x="-42" y="5" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="30" value="&lt;span style=&quot;color: rgb(0, 0, 0); font-family: Helvetica; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: center; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;&quot;&gt;[0, M]&lt;/span&gt;" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0;exitY=0;exitDx=0;exitDy=0;entryX=1;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="12" target="5" parent="1">
          <mxGeometry x="-0.625" y="-21" relative="1" as="geometry">
            <mxPoint x="3220" y="-179" as="targetPoint" />
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="31" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="30">
          <mxGeometry x="0.3479" y="3" relative="1" as="geometry">
            <mxPoint x="-22" y="16" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="32" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=1;exitDx=0;exitDy=0;endArrow=none;endFill=0;entryX=0;entryY=0.75;entryDx=0;entryDy=0;" edge="1" source="12" target="22" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="33" value="[ 0, 1 ]&amp;nbsp;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="32">
          <mxGeometry x="-0.6377" relative="1" as="geometry">
            <mxPoint x="-273" y="25" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="34" value="[ 1, 1 ]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="32">
          <mxGeometry x="0.592" y="-4" relative="1" as="geometry">
            <mxPoint x="293" y="20" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="35" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.75;exitY=1;exitDx=0;exitDy=0;endArrow=none;endFill=0;entryX=0.75;entryY=0;entryDx=0;entryDy=0;" edge="1" source="38" target="21" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="36" value="&lt;span style=&quot;color: rgb(0, 0, 0); font-family: Helvetica; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: center; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;&quot;&gt;[1,1]&lt;/span&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="35">
          <mxGeometry x="0.0251" y="2" relative="1" as="geometry">
            <mxPoint x="28" y="-214" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="37" value="Answer" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="4720" y="-778.5" width="160" height="152.5" as="geometry" />
        </mxCell>
        <mxCell id="38" value="&lt;b&gt;+ id&lt;/b&gt;&lt;br&gt;+ activity&lt;br&gt;+ status:boolean&lt;br&gt;+ user&lt;br&gt;+ date&lt;br&gt;+ last modified" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="37">
          <mxGeometry y="50" width="160" height="102.5" as="geometry" />
        </mxCell>
        <mxCell id="39" value="Concern" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="2790" y="-826" width="160" height="130" as="geometry" />
        </mxCell>
        <mxCell id="40" value="+ &lt;b&gt;id&lt;/b&gt;&lt;br&gt;+ description&lt;br&gt;+ explanation&lt;br&gt;+ user&lt;br&gt;+ date" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="39">
          <mxGeometry y="50" width="160" height="80" as="geometry" />
        </mxCell>
        <mxCell id="41" value="[1, 1]" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0;exitY=0.5;exitDx=0;exitDy=0;endArrow=none;endFill=0;fontSize=20;entryX=0.25;entryY=0;entryDx=0;entryDy=0;" edge="1" source="20" target="39" parent="1">
          <mxGeometry x="0.8447" y="30" relative="1" as="geometry">
            <mxPoint as="offset" />
            <mxPoint x="3120" y="-698" as="sourcePoint" />
            <mxPoint x="3120" y="-587" as="targetPoint" />
            <Array as="points">
              <mxPoint x="2830" y="-975" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="42" value="[ 0, M ]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="41">
          <mxGeometry x="-0.5901" y="4" relative="1" as="geometry">
            <mxPoint x="23" y="-19" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="43" value="post" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="41">
          <mxGeometry x="0.0874" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="44" value="[1, 1]" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0;exitDx=0;exitDy=0;endArrow=none;endFill=0;fontSize=20;entryX=0.5;entryY=0;entryDx=0;entryDy=0;" edge="1" source="19" target="37" parent="1">
          <mxGeometry x="0.9777" y="40" relative="1" as="geometry">
            <mxPoint as="offset" />
            <mxPoint x="3430" y="-1010" as="sourcePoint" />
            <mxPoint x="3120" y="-820" as="targetPoint" />
            <Array as="points">
              <mxPoint x="4800" y="-1060" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="45" value="[ 0, M ]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="44">
          <mxGeometry x="-0.5901" y="4" relative="1" as="geometry">
            <mxPoint x="-346" y="-9" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="46" value="&lt;span style=&quot;color: rgb(0, 0, 0); font-family: Helvetica; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: center; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;&quot;&gt;[0, M]&lt;/span&gt;" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.5;exitY=0;exitDx=0;exitDy=0;endArrow=none;endFill=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;" edge="1" source="26" target="40" parent="1">
          <mxGeometry x="0.8584" y="40" relative="1" as="geometry">
            <mxPoint x="3050" y="-208" as="sourcePoint" />
            <mxPoint x="3160" y="-438" as="targetPoint" />
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="47" value="[0, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="46">
          <mxGeometry x="-0.6" relative="1" as="geometry">
            <mxPoint x="-30" y="25" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="48" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;" edge="1" source="49" target="19" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="49" value="La entidad User pertenece a un servicio externo de autenticación." style="shape=note;size=20;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="3055" y="-1169" width="190" height="70" as="geometry" />
        </mxCell>
        <mxCell id="50" value="Rol" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#e3c800;fontColor=#000000;strokeColor=#B09500;" vertex="1" parent="1">
          <mxGeometry x="3290" y="-726" width="160" height="100" as="geometry" />
        </mxCell>
        <mxCell id="51" value="+ &lt;b&gt;id&lt;/b&gt;&lt;br&gt;+ name" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="50">
          <mxGeometry y="50" width="160" height="50" as="geometry" />
        </mxCell>
        <mxCell id="52" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="53" target="50" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="53" value="URL" style="shape=rhombus;perimeter=rhombusPerimeter;whiteSpace=wrap;html=1;align=center;fillColor=#bac8d3;strokeColor=#23445d;" vertex="1" parent="1">
          <mxGeometry x="3130" y="-706" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="54" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.75;exitY=1;exitDx=0;exitDy=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="20" target="53" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="55" value="&lt;b&gt;URL:&lt;br&gt;- Un usuario puede tener un rol en un nivel&lt;br&gt;- Un nivel puede tener muchos usuarios con muchos roles&lt;br&gt;- Un rol puede tener muchos usurios en un solo nivel&lt;br&gt;&lt;/b&gt;" style="text;html=1;strokeColor=none;fillColor=none;align=left;verticalAlign=top;whiteSpace=wrap;rounded=0;" vertex="1" parent="1">
          <mxGeometry x="3200" y="-579.5" width="350" height="80" as="geometry" />
        </mxCell>
        <mxCell id="56" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;entryX=1;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="57" target="51" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="57" value="URO" style="shape=rhombus;perimeter=rhombusPerimeter;whiteSpace=wrap;html=1;align=center;fillColor=#bac8d3;strokeColor=#23445d;" vertex="1" parent="1">
          <mxGeometry x="3520" y="-706" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="58" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.75;exitY=0;exitDx=0;exitDy=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="2" target="57" parent="1">
          <mxGeometry relative="1" as="geometry">
            <Array as="points">
              <mxPoint x="3580" y="-646" />
            </Array>
            <mxPoint x="3040" y="-359" as="sourcePoint" />
            <mxPoint x="3200" y="-636" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="59" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;entryX=1;entryY=0.5;entryDx=0;entryDy=0;endArrow=none;endFill=0;exitX=0.5;exitY=0;exitDx=0;exitDy=0;" edge="1" source="57" target="20" parent="1">
          <mxGeometry relative="1" as="geometry">
            <mxPoint x="3530" y="-666" as="sourcePoint" />
            <mxPoint x="3460" y="-666" as="targetPoint" />
            <Array as="points">
              <mxPoint x="3580" y="-975" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="60" value="&lt;b&gt;URO:&lt;br&gt;- Un usuario puede tener un rol en una organización&lt;br&gt;- Una organización puede tener muchos usuarios con muchos roles&lt;br&gt;- Un rol puede tener muchos usuarios en una sola organización&lt;br&gt;&lt;/b&gt;" style="text;html=1;strokeColor=none;fillColor=none;align=left;verticalAlign=top;whiteSpace=wrap;rounded=0;" vertex="1" parent="1">
          <mxGeometry x="3200" y="-484.5" width="350" height="94.5" as="geometry" />
        </mxCell>
        <mxCell id="61" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;" edge="1" source="66" target="91" parent="1">
          <mxGeometry relative="1" as="geometry">
            <mxPoint x="3914.068540681446" y="-290.119882991896" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="62" value="Votes" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="2558" y="-1055" width="160" height="130" as="geometry" />
        </mxCell>
        <mxCell id="63" value="+ &lt;b&gt;id&lt;/b&gt;&lt;br&gt;+ user&lt;br&gt;+ date&lt;br&gt;+ entity" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="62">
          <mxGeometry y="50" width="160" height="80" as="geometry" />
        </mxCell>
        <mxCell id="64" value="Entity" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="2558" y="-880" width="160" height="140" as="geometry" />
        </mxCell>
        <mxCell id="65" value="&lt;b&gt;+ id&lt;/b&gt;&lt;br&gt;+ title&lt;div&gt;+ votable: boolean&lt;/div&gt;" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="64">
          <mxGeometry y="50" width="160" height="90" as="geometry" />
        </mxCell>
        <mxCell id="66" value="ActivityTypeVersion" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="3998" y="-350" width="170" height="202" as="geometry" />
        </mxCell>
        <mxCell id="67" value="&lt;b&gt;+ id&lt;br&gt;&lt;/b&gt;+ activityType&lt;br&gt;+ status&lt;br&gt;- model.json&lt;br&gt;- template.js&lt;br&gt;- README.md&lt;br&gt;+ versionNumber&lt;br&gt;+stagedDate&lt;br&gt;+ lastModifiedStatusDate" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="66">
          <mxGeometry y="50" width="170" height="152" as="geometry" />
        </mxCell>
        <mxCell id="68" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;entryX=0.5;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="9" target="66" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="69" value="[1, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" vertex="1" connectable="0" parent="68">
          <mxGeometry x="-0.3166" y="1" relative="1" as="geometry">
            <mxPoint x="-38" y="-62" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="70" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" vertex="1" connectable="0" parent="1">
          <mxGeometry x="4030" y="-463" as="geometry">
            <mxPoint x="20" y="4" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="71" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;entryX=1;entryY=0.5;entryDx=0;entryDy=0;endArrow=none;endFill=0;exitX=0;exitY=1;exitDx=0;exitDy=0;" edge="1" source="67" target="74" parent="1">
          <mxGeometry relative="1" as="geometry">
            <mxPoint x="3998" y="-150.75" as="sourcePoint" />
            <mxPoint x="3850" y="-157" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="72" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" vertex="1" connectable="0" parent="71">
          <mxGeometry x="-0.7854" relative="1" as="geometry">
            <mxPoint x="-12" y="22" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="73" value="[0, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" vertex="1" connectable="0" parent="71">
          <mxGeometry x="0.5219" y="-4" relative="1" as="geometry">
            <mxPoint x="1" y="-20" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="74" value="ActivityTypeVersionStatus" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="3680" y="-206" width="170" height="114" as="geometry" />
        </mxCell>
        <mxCell id="75" value="&lt;b&gt;+ id&lt;br&gt;&lt;/b&gt;+ title&lt;br&gt;+ description" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="74">
          <mxGeometry y="50" width="170" height="64" as="geometry" />
        </mxCell>
        <mxCell id="76" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.5;exitY=1;exitDx=0;exitDy=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="80" target="84" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="77" value="[0, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="76">
          <mxGeometry x="-0.8566" y="-2" relative="1" as="geometry">
            <mxPoint x="33" y="20" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="78" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="76">
          <mxGeometry x="0.8668" y="-1" relative="1" as="geometry">
            <mxPoint x="26" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="79" value="Content" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="4470" y="-430" width="170" height="121" as="geometry" />
        </mxCell>
        <mxCell id="80" value="&lt;b&gt;+ id&lt;br&gt;&lt;/b&gt;+ ActivityTypeVersion&lt;br&gt;+ model.json (filled)" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="79">
          <mxGeometry y="50" width="170" height="71" as="geometry" />
        </mxCell>
        <mxCell id="81" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;entryX=0;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;exitX=1;exitY=0;exitDx=0;exitDy=0;" edge="1" source="67" target="80" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="82" value="&lt;span style=&quot;font-size: 20px;&quot;&gt;[0, M]&lt;/span&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="81">
          <mxGeometry x="-0.7683" y="2" relative="1" as="geometry">
            <mxPoint x="12" y="-17" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="83" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="81">
          <mxGeometry x="0.7704" y="-2" relative="1" as="geometry">
            <mxPoint x="-10" y="18" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="84" value="Images" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="4470" y="-190" width="170" height="124" as="geometry" />
        </mxCell>
        <mxCell id="85" value="&lt;b&gt;+ id&lt;br&gt;+ &lt;/b&gt;imageName&lt;br&gt;+ content" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="84">
          <mxGeometry y="50" width="170" height="74" as="geometry" />
        </mxCell>
        <mxCell id="86" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0;exitY=0.25;exitDx=0;exitDy=0;entryX=0.99;entryY=0.193;entryDx=0;entryDy=0;entryPerimeter=0;endArrow=none;endFill=0;" edge="1" source="19" target="62" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="87" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;votes&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="86">
          <mxGeometry x="0.074" y="-1" relative="1" as="geometry">
            <mxPoint x="-3" y="1" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="88" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;[ 0, M ]&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="86">
          <mxGeometry x="-0.9046" relative="1" as="geometry">
            <mxPoint x="-37" y="-20" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="89" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="86">
          <mxGeometry x="0.8975" y="-1" relative="1" as="geometry">
            <mxPoint x="13" y="-19" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="90" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.5;exitY=1;exitDx=0;exitDy=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="63" target="64" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="91" value="FILE SYSTEM" style="sketch=0;outlineConnect=0;fontColor=#232F3E;gradientColor=none;fillColor=#000000;strokeColor=none;dashed=0;verticalLabelPosition=bottom;verticalAlign=top;align=center;html=1;fontSize=18;fontStyle=1;aspect=fixed;pointerEvents=1;shape=mxgraph.aws4.file_system;" vertex="1" parent="1">
          <mxGeometry x="4210" y="-228.5" width="167.77" height="157.01" as="geometry" />
        </mxCell>
        <mxCell id="92" value="- THUMBNAIL&lt;br&gt;- CONTENT IMAGES" style="text;html=1;align=center;verticalAlign=middle;resizable=0;points=[];autosize=1;strokeColor=none;fillColor=none;" vertex="1" parent="1">
          <mxGeometry x="4223.89" y="-130" width="140" height="40" as="geometry" />
        </mxCell>
        <mxCell id="93" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;" edge="1" source="85" target="91" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="94" value="[]" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.75;exitY=1;exitDx=0;exitDy=0;endArrow=none;endFill=0;" edge="1" source="9" target="21" parent="1">
          <mxGeometry relative="1" as="geometry">
            <Array as="points">
              <mxPoint x="4126" y="-520" />
              <mxPoint x="4780" y="-520" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="95" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;[0, M]&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="94">
          <mxGeometry x="-0.9447" y="3" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="96" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;[1,1]&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="94">
          <mxGeometry x="0.9483" relative="1" as="geometry">
            <mxPoint x="10" as="offset" />
          </mxGeometry>
        </mxCell>
      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
<?xml version="1.0" encoding="UTF-8"?>
<mxfile host="app.diagrams.net" agent="Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:135.0) Gecko/20100101 Firefox/135.0" version="26.0.11">
  <diagram name="Page-1" id="B3IvyyJTzz6X2-31m1zG">
    <mxGraphModel dx="245" dy="3003" grid="1" gridSize="10" guides="1" tooltips="1" connect="1" arrows="1" fold="1" page="1" pageScale="1" pageWidth="827" pageHeight="1169" math="0" shadow="0">
      <root>
        <mxCell id="0" />
        <mxCell id="1" parent="0" />
        <mxCell id="2" value="Organization" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1">
          <mxGeometry x="3460" y="-210" width="160" height="120" as="geometry" />
        </mxCell>
        <mxCell id="3" value="&lt;b&gt;+ id&lt;/b&gt;&lt;br&gt;+ name&lt;br&gt;+ email&lt;br&gt;+ description" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="2">
          <mxGeometry y="50" width="160" height="70" as="geometry" />
        </mxCell>
        <mxCell id="4" value="Reference" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="2510" y="-190" width="160" height="139" as="geometry" />
        </mxCell>
        <mxCell id="5" value="&lt;b&gt;+ id&lt;/b&gt;&lt;br&gt;+ url&amp;nbsp;&lt;br&gt;+ description&lt;br&gt;+ level&lt;br&gt;+ title" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="4">
          <mxGeometry y="50" width="160" height="89" as="geometry" />
        </mxCell>
        <mxCell id="6" value="[1, 1]" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0;exitY=1;exitDx=0;exitDy=0;endArrow=none;endFill=0;fontSize=20;entryX=1;entryY=0.5;entryDx=0;entryDy=0;" edge="1" source="3" target="12" parent="1">
          <mxGeometry x="0.6471" relative="1" as="geometry">
            <mxPoint as="offset" />
            <mxPoint x="3150" y="-638" as="sourcePoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="7" value="[ 0, M ]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="6">
          <mxGeometry x="-0.5901" y="4" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="8" value="ActivityType" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="3998" y="-752" width="170" height="118" as="geometry" />
        </mxCell>
        <mxCell id="9" value="&lt;b&gt;+ id&lt;br&gt;+ name&lt;br&gt;&lt;/b&gt;+ description&lt;br&gt;+ creator" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="8">
          <mxGeometry y="50" width="170" height="68" as="geometry" />
        </mxCell>
        <mxCell id="10" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.75;exitY=0;exitDx=0;exitDy=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;endArrow=none;endFill=0;edgeStyle=orthogonalEdgeStyle;" edge="1" source="11" target="53" parent="1">
          <mxGeometry relative="1" as="geometry">
            <Array as="points">
              <mxPoint x="2950" y="-310" />
              <mxPoint x="3190" y="-310" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="11" value="Level" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;direction=east;fillColor=#e1d5e7;strokeColor=#9673a6;" vertex="1" parent="1">
          <mxGeometry x="2830" y="-190" width="160" height="149" as="geometry">
            <mxRectangle x="290" y="210" width="80" height="50" as="alternateBounds" />
          </mxGeometry>
        </mxCell>
        <mxCell id="12" value="+ &lt;b&gt;id&lt;br&gt;&lt;/b&gt;+ name&lt;br&gt;+ description&lt;br&gt;+ organization&lt;br&gt;+ parent" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="11">
          <mxGeometry y="50" width="160" height="99" as="geometry" />
        </mxCell>
        <mxCell id="13" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0.25;exitDx=0;exitDy=0;entryX=1;entryY=0.25;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" parent="11" source="11" target="12">
          <mxGeometry relative="1" as="geometry">
            <Array as="points">
              <mxPoint x="260" y="37" />
              <mxPoint x="260" y="75" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="14" value="[0, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="13">
          <mxGeometry x="-0.6448" y="4" relative="1" as="geometry">
            <mxPoint y="4" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="15" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0.25;exitDx=0;exitDy=0;endArrow=none;endFill=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;" edge="1" source="19" target="8" parent="1">
          <mxGeometry relative="1" as="geometry">
            <Array as="points">
              <mxPoint x="4080" y="-1030" />
              <mxPoint x="4080" y="-752" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="16" value="[ 0, M ]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="15">
          <mxGeometry x="-0.9621" y="-2" relative="1" as="geometry">
            <mxPoint x="25" y="18" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="17" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;[ 1, 1 ]&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="15">
          <mxGeometry x="0.9687" y="-2" relative="1" as="geometry">
            <mxPoint x="-38" y="-2" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="18" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;create&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="15">
          <mxGeometry x="-0.0003" y="3" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="19" value="User" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#e3c800;fontColor=#000000;strokeColor=#B09500;" vertex="1" parent="1">
          <mxGeometry x="2990" y="-1060" width="160" height="120" as="geometry" />
        </mxCell>
        <mxCell id="20" value="+ &lt;b&gt;id&lt;/b&gt;&lt;br&gt;+ email&lt;br&gt;+ username&lt;br&gt;+ password" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="19">
          <mxGeometry y="50" width="160" height="70" as="geometry" />
        </mxCell>
        <mxCell id="21" value="Activity" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="4720" y="-162" width="160" height="147.5" as="geometry" />
        </mxCell>
        <mxCell id="22" value="&lt;b&gt;+ id&lt;/b&gt;&lt;div&gt;+ content&amp;nbsp;&lt;br&gt;+ description&lt;br&gt;+ level&lt;/div&gt;" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="21">
          <mxGeometry y="50" width="160" height="97.5" as="geometry" />
        </mxCell>
        <mxCell id="23" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;endArrow=none;endFill=0;entryX=0.25;entryY=0;entryDx=0;entryDy=0;exitX=1;exitY=0;exitDx=0;exitDy=0;" edge="1" source="80" target="21" parent="1">
          <mxGeometry relative="1" as="geometry">
            <mxPoint x="5180" y="-103.5" as="sourcePoint" />
            <Array as="points">
              <mxPoint x="4760" y="-210" />
            </Array>
            <mxPoint x="4350" y="-100" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="24" value="[1,1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="23">
          <mxGeometry x="0.5886" y="-2" relative="1" as="geometry">
            <mxPoint x="-28" y="8" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="25" value="[1, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="23">
          <mxGeometry x="-0.5306" y="2" relative="1" as="geometry">
            <mxPoint x="-4" y="-24" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="26" value="Tag" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#fff2cc;strokeColor=#d6b656;" vertex="1" parent="1">
          <mxGeometry x="2790" y="-470" width="160" height="120" as="geometry" />
        </mxCell>
        <mxCell id="27" value="&lt;b&gt;+id&lt;/b&gt;&lt;br&gt;+name" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="26">
          <mxGeometry y="50" width="160" height="70" as="geometry" />
        </mxCell>
        <mxCell id="28" value="&lt;span style=&quot;color: rgb(0, 0, 0); font-family: Helvetica; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: center; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;&quot;&gt;[0, M]&lt;/span&gt;" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.5;exitY=1;exitDx=0;exitDy=0;endArrow=none;endFill=0;entryX=0.25;entryY=0;entryDx=0;entryDy=0;" edge="1" source="27" target="11" parent="1">
          <mxGeometry x="0.625" y="110" relative="1" as="geometry">
            <mxPoint x="2910" y="-270" as="sourcePoint" />
            <mxPoint x="2964.8275862068967" y="-65" as="targetPoint" />
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="29" value="[1, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="28">
          <mxGeometry x="-0.6" relative="1" as="geometry">
            <mxPoint x="-42" y="5" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="30" value="&lt;span style=&quot;color: rgb(0, 0, 0); font-family: Helvetica; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: center; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;&quot;&gt;[0, M]&lt;/span&gt;" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0;exitY=0;exitDx=0;exitDy=0;entryX=1;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="12" target="5" parent="1">
          <mxGeometry x="-0.625" y="-21" relative="1" as="geometry">
            <mxPoint x="3220" y="-179" as="targetPoint" />
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="31" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="30">
          <mxGeometry x="0.3479" y="3" relative="1" as="geometry">
            <mxPoint x="-22" y="16" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="32" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=1;exitDx=0;exitDy=0;endArrow=none;endFill=0;entryX=0;entryY=0.75;entryDx=0;entryDy=0;" edge="1" source="12" target="22" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="33" value="[ 0, 1 ]&amp;nbsp;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="32">
          <mxGeometry x="-0.6377" relative="1" as="geometry">
            <mxPoint x="-273" y="25" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="34" value="[ 1, 1 ]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="32">
          <mxGeometry x="0.592" y="-4" relative="1" as="geometry">
            <mxPoint x="293" y="20" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="35" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.75;exitY=1;exitDx=0;exitDy=0;endArrow=none;endFill=0;entryX=0.75;entryY=0;entryDx=0;entryDy=0;" edge="1" source="38" target="21" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="36" value="&lt;span style=&quot;color: rgb(0, 0, 0); font-family: Helvetica; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: center; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;&quot;&gt;[1,1]&lt;/span&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="35">
          <mxGeometry x="0.0251" y="2" relative="1" as="geometry">
            <mxPoint x="28" y="-214" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="37" value="Answer" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="4720" y="-778.5" width="160" height="152.5" as="geometry" />
        </mxCell>
        <mxCell id="38" value="&lt;b&gt;+ id&lt;/b&gt;&lt;br&gt;+ activity&lt;br&gt;+ status:boolean&lt;br&gt;+ user&lt;br&gt;+ date&lt;br&gt;+ last modified" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="37">
          <mxGeometry y="50" width="160" height="102.5" as="geometry" />
        </mxCell>
        <mxCell id="39" value="Concern" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="2790" y="-826" width="160" height="130" as="geometry" />
        </mxCell>
        <mxCell id="40" value="+ &lt;b&gt;id&lt;/b&gt;&lt;br&gt;+ description&lt;br&gt;+ explanation&lt;br&gt;+ user&lt;br&gt;+ date" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="39">
          <mxGeometry y="50" width="160" height="80" as="geometry" />
        </mxCell>
        <mxCell id="41" value="[1, 1]" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0;exitY=0.5;exitDx=0;exitDy=0;endArrow=none;endFill=0;fontSize=20;entryX=0.25;entryY=0;entryDx=0;entryDy=0;" edge="1" source="20" target="39" parent="1">
          <mxGeometry x="0.8447" y="30" relative="1" as="geometry">
            <mxPoint as="offset" />
            <mxPoint x="3120" y="-698" as="sourcePoint" />
            <mxPoint x="3120" y="-587" as="targetPoint" />
            <Array as="points">
              <mxPoint x="2830" y="-975" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="42" value="[ 0, M ]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="41">
          <mxGeometry x="-0.5901" y="4" relative="1" as="geometry">
            <mxPoint x="23" y="-19" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="43" value="post" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="41">
          <mxGeometry x="0.0874" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="44" value="[1, 1]" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0;exitDx=0;exitDy=0;endArrow=none;endFill=0;fontSize=20;entryX=0.5;entryY=0;entryDx=0;entryDy=0;" edge="1" source="19" target="37" parent="1">
          <mxGeometry x="0.9777" y="40" relative="1" as="geometry">
            <mxPoint as="offset" />
            <mxPoint x="3430" y="-1010" as="sourcePoint" />
            <mxPoint x="3120" y="-820" as="targetPoint" />
            <Array as="points">
              <mxPoint x="4800" y="-1060" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="45" value="[ 0, M ]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="44">
          <mxGeometry x="-0.5901" y="4" relative="1" as="geometry">
            <mxPoint x="-346" y="-9" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="46" value="&lt;span style=&quot;color: rgb(0, 0, 0); font-family: Helvetica; font-size: 20px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: normal; orphans: 2; text-align: center; text-indent: 0px; text-transform: none; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; background-color: rgb(255, 255, 255); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; float: none; display: inline !important;&quot;&gt;[0, M]&lt;/span&gt;" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.5;exitY=0;exitDx=0;exitDy=0;endArrow=none;endFill=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;" edge="1" source="26" target="40" parent="1">
          <mxGeometry x="0.8584" y="40" relative="1" as="geometry">
            <mxPoint x="3050" y="-208" as="sourcePoint" />
            <mxPoint x="3160" y="-438" as="targetPoint" />
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="47" value="[0, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="46">
          <mxGeometry x="-0.6" relative="1" as="geometry">
            <mxPoint x="-30" y="25" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="48" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;" edge="1" source="49" target="19" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="49" value="La entidad User pertenece a un servicio externo de autenticación." style="shape=note;size=20;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="3055" y="-1169" width="190" height="70" as="geometry" />
        </mxCell>
        <mxCell id="50" value="Rol" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#e3c800;fontColor=#000000;strokeColor=#B09500;" vertex="1" parent="1">
          <mxGeometry x="3290" y="-726" width="160" height="100" as="geometry" />
        </mxCell>
        <mxCell id="51" value="+ &lt;b&gt;id&lt;/b&gt;&lt;br&gt;+ name" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="50">
          <mxGeometry y="50" width="160" height="50" as="geometry" />
        </mxCell>
        <mxCell id="52" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=1;exitY=0.5;exitDx=0;exitDy=0;entryX=0;entryY=0.5;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="53" target="50" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="53" value="URL" style="shape=rhombus;perimeter=rhombusPerimeter;whiteSpace=wrap;html=1;align=center;fillColor=#bac8d3;strokeColor=#23445d;" vertex="1" parent="1">
          <mxGeometry x="3130" y="-706" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="54" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.75;exitY=1;exitDx=0;exitDy=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="20" target="53" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="55" value="&lt;b&gt;URL:&lt;br&gt;- Un usuario puede tener un rol en un nivel&lt;br&gt;- Un nivel puede tener muchos usuarios con muchos roles&lt;br&gt;- Un rol puede tener muchos usurios en un solo nivel&lt;br&gt;&lt;/b&gt;" style="text;html=1;strokeColor=none;fillColor=none;align=left;verticalAlign=top;whiteSpace=wrap;rounded=0;" vertex="1" parent="1">
          <mxGeometry x="3200" y="-579.5" width="350" height="80" as="geometry" />
        </mxCell>
        <mxCell id="56" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;entryX=1;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="57" target="51" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="57" value="URO" style="shape=rhombus;perimeter=rhombusPerimeter;whiteSpace=wrap;html=1;align=center;fillColor=#bac8d3;strokeColor=#23445d;" vertex="1" parent="1">
          <mxGeometry x="3520" y="-706" width="120" height="60" as="geometry" />
        </mxCell>
        <mxCell id="58" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.75;exitY=0;exitDx=0;exitDy=0;entryX=0.5;entryY=1;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="2" target="57" parent="1">
          <mxGeometry relative="1" as="geometry">
            <Array as="points">
              <mxPoint x="3580" y="-646" />
            </Array>
            <mxPoint x="3040" y="-359" as="sourcePoint" />
            <mxPoint x="3200" y="-636" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="59" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;entryX=1;entryY=0.5;entryDx=0;entryDy=0;endArrow=none;endFill=0;exitX=0.5;exitY=0;exitDx=0;exitDy=0;" edge="1" source="57" target="20" parent="1">
          <mxGeometry relative="1" as="geometry">
            <mxPoint x="3530" y="-666" as="sourcePoint" />
            <mxPoint x="3460" y="-666" as="targetPoint" />
            <Array as="points">
              <mxPoint x="3580" y="-975" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="60" value="&lt;b&gt;URO:&lt;br&gt;- Un usuario puede tener un rol en una organización&lt;br&gt;- Una organización puede tener muchos usuarios con muchos roles&lt;br&gt;- Un rol puede tener muchos usuarios en una sola organización&lt;br&gt;&lt;/b&gt;" style="text;html=1;strokeColor=none;fillColor=none;align=left;verticalAlign=top;whiteSpace=wrap;rounded=0;" vertex="1" parent="1">
          <mxGeometry x="3200" y="-484.5" width="350" height="94.5" as="geometry" />
        </mxCell>
        <mxCell id="61" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;" edge="1" source="66" target="91" parent="1">
          <mxGeometry relative="1" as="geometry">
            <mxPoint x="3914.068540681446" y="-290.119882991896" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="62" value="Votes" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#d5e8d4;strokeColor=#82b366;" vertex="1" parent="1">
          <mxGeometry x="2558" y="-1055" width="160" height="130" as="geometry" />
        </mxCell>
        <mxCell id="63" value="+ &lt;b&gt;id&lt;/b&gt;&lt;br&gt;+ user&lt;br&gt;+ date&lt;br&gt;+ entity" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="62">
          <mxGeometry y="50" width="160" height="80" as="geometry" />
        </mxCell>
        <mxCell id="64" value="Entity" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;fillColor=#f8cecc;strokeColor=#b85450;" vertex="1" parent="1">
          <mxGeometry x="2558" y="-880" width="160" height="140" as="geometry" />
        </mxCell>
        <mxCell id="65" value="&lt;b&gt;+ id&lt;/b&gt;&lt;br&gt;+ title&lt;div&gt;+ votable: boolean&lt;/div&gt;" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="64">
          <mxGeometry y="50" width="160" height="90" as="geometry" />
        </mxCell>
        <mxCell id="66" value="ActivityTypeVersion" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="3998" y="-350" width="170" height="202" as="geometry" />
        </mxCell>
        <mxCell id="67" value="&lt;b&gt;+ id&lt;br&gt;&lt;/b&gt;+ activityType&lt;br&gt;+ status&lt;br&gt;- model.json&lt;br&gt;- template.js&lt;br&gt;- README.md&lt;br&gt;+ versionNumber&lt;br&gt;+stagedDate&lt;br&gt;+ lastModifiedStatusDate" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="66">
          <mxGeometry y="50" width="170" height="152" as="geometry" />
        </mxCell>
        <mxCell id="68" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;entryX=0.5;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="9" target="66" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="69" value="[1, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" vertex="1" connectable="0" parent="68">
          <mxGeometry x="-0.3166" y="1" relative="1" as="geometry">
            <mxPoint x="-38" y="-62" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="70" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" vertex="1" connectable="0" parent="1">
          <mxGeometry x="4030" y="-463" as="geometry">
            <mxPoint x="20" y="4" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="71" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;entryX=1;entryY=0.5;entryDx=0;entryDy=0;endArrow=none;endFill=0;exitX=0;exitY=1;exitDx=0;exitDy=0;" edge="1" source="67" target="74" parent="1">
          <mxGeometry relative="1" as="geometry">
            <mxPoint x="3998" y="-150.75" as="sourcePoint" />
            <mxPoint x="3850" y="-157" as="targetPoint" />
          </mxGeometry>
        </mxCell>
        <mxCell id="72" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" vertex="1" connectable="0" parent="71">
          <mxGeometry x="-0.7854" relative="1" as="geometry">
            <mxPoint x="-12" y="22" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="73" value="[0, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" vertex="1" connectable="0" parent="71">
          <mxGeometry x="0.5219" y="-4" relative="1" as="geometry">
            <mxPoint x="1" y="-20" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="74" value="ActivityTypeVersionStatus" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="3680" y="-206" width="170" height="114" as="geometry" />
        </mxCell>
        <mxCell id="75" value="&lt;b&gt;+ id&lt;br&gt;&lt;/b&gt;+ title&lt;br&gt;+ description" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="74">
          <mxGeometry y="50" width="170" height="64" as="geometry" />
        </mxCell>
        <mxCell id="76" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.5;exitY=1;exitDx=0;exitDy=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="80" target="84" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="77" value="[0, M]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="76">
          <mxGeometry x="-0.8566" y="-2" relative="1" as="geometry">
            <mxPoint x="33" y="20" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="78" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="76">
          <mxGeometry x="0.8668" y="-1" relative="1" as="geometry">
            <mxPoint x="26" y="-12" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="79" value="Content" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="4470" y="-430" width="170" height="121" as="geometry" />
        </mxCell>
        <mxCell id="80" value="&lt;b&gt;+ id&lt;br&gt;&lt;/b&gt;+ ActivityTypeVersion&lt;br&gt;+ model.json (filled)" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="79">
          <mxGeometry y="50" width="170" height="71" as="geometry" />
        </mxCell>
        <mxCell id="81" style="rounded=0;orthogonalLoop=1;jettySize=auto;html=1;entryX=0;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;exitX=1;exitY=0;exitDx=0;exitDy=0;" edge="1" source="67" target="80" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="82" value="&lt;span style=&quot;font-size: 20px;&quot;&gt;[0, M]&lt;/span&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="81">
          <mxGeometry x="-0.7683" y="2" relative="1" as="geometry">
            <mxPoint x="12" y="-17" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="83" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="81">
          <mxGeometry x="0.7704" y="-2" relative="1" as="geometry">
            <mxPoint x="-10" y="18" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="84" value="Images" style="swimlane;childLayout=stackLayout;horizontal=1;startSize=50;horizontalStack=0;rounded=1;fontSize=14;fontStyle=0;strokeWidth=2;resizeParent=0;resizeLast=1;shadow=0;dashed=0;align=center;arcSize=4;whiteSpace=wrap;html=1;" vertex="1" parent="1">
          <mxGeometry x="4470" y="-190" width="170" height="124" as="geometry" />
        </mxCell>
        <mxCell id="85" value="&lt;b&gt;+ id&lt;br&gt;+ &lt;/b&gt;imageName&lt;br&gt;+ content" style="align=left;strokeColor=none;fillColor=none;spacingLeft=4;fontSize=12;verticalAlign=top;resizable=0;rotatable=0;part=1;html=1;" vertex="1" parent="84">
          <mxGeometry y="50" width="170" height="74" as="geometry" />
        </mxCell>
        <mxCell id="86" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0;exitY=0.25;exitDx=0;exitDy=0;entryX=0.99;entryY=0.193;entryDx=0;entryDy=0;entryPerimeter=0;endArrow=none;endFill=0;" edge="1" source="19" target="62" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="87" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;votes&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="86">
          <mxGeometry x="0.074" y="-1" relative="1" as="geometry">
            <mxPoint x="-3" y="1" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="88" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;[ 0, M ]&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="86">
          <mxGeometry x="-0.9046" relative="1" as="geometry">
            <mxPoint x="-37" y="-20" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="89" value="[1, 1]" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];fontSize=20;" connectable="0" vertex="1" parent="86">
          <mxGeometry x="0.8975" y="-1" relative="1" as="geometry">
            <mxPoint x="13" y="-19" as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="90" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.5;exitY=1;exitDx=0;exitDy=0;entryX=0.5;entryY=0;entryDx=0;entryDy=0;endArrow=none;endFill=0;" edge="1" source="63" target="64" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="91" value="FILE SYSTEM" style="sketch=0;outlineConnect=0;fontColor=#232F3E;gradientColor=none;fillColor=#000000;strokeColor=none;dashed=0;verticalLabelPosition=bottom;verticalAlign=top;align=center;html=1;fontSize=18;fontStyle=1;aspect=fixed;pointerEvents=1;shape=mxgraph.aws4.file_system;" vertex="1" parent="1">
          <mxGeometry x="4210" y="-228.5" width="167.77" height="157.01" as="geometry" />
        </mxCell>
        <mxCell id="92" value="- THUMBNAIL&lt;br&gt;- CONTENT IMAGES" style="text;html=1;align=center;verticalAlign=middle;resizable=0;points=[];autosize=1;strokeColor=none;fillColor=none;" vertex="1" parent="1">
          <mxGeometry x="4223.89" y="-130" width="140" height="40" as="geometry" />
        </mxCell>
        <mxCell id="93" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;" edge="1" source="85" target="91" parent="1">
          <mxGeometry relative="1" as="geometry" />
        </mxCell>
        <mxCell id="94" value="[]" style="edgeStyle=orthogonalEdgeStyle;rounded=0;orthogonalLoop=1;jettySize=auto;html=1;exitX=0.75;exitY=1;exitDx=0;exitDy=0;endArrow=none;endFill=0;" edge="1" source="9" target="21" parent="1">
          <mxGeometry relative="1" as="geometry">
            <Array as="points">
              <mxPoint x="4126" y="-520" />
              <mxPoint x="4780" y="-520" />
            </Array>
          </mxGeometry>
        </mxCell>
        <mxCell id="95" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;[0, M]&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="94">
          <mxGeometry x="-0.9447" y="3" relative="1" as="geometry">
            <mxPoint as="offset" />
          </mxGeometry>
        </mxCell>
        <mxCell id="96" value="&lt;font style=&quot;font-size: 20px;&quot;&gt;[1,1]&lt;/font&gt;" style="edgeLabel;html=1;align=center;verticalAlign=middle;resizable=0;points=[];" connectable="0" vertex="1" parent="94">
          <mxGeometry x="0.9483" relative="1" as="geometry">
            <mxPoint x="10" as="offset" />
          </mxGeometry>
        </mxCell>
      </root>
    </mxGraphModel>
  </diagram>
</mxfile>
```

# Excalidraw Data

## Text Elements
API ^nbPjBGeG

2. Recurso ^MA7e3ZEM

3. Servicio ^5Jjkg1Qf

4. Acceso ^mDZmUstl

5. Base de datos ^dxLd4aK5

6. Transformador ^1BHBEvif

0. Petición HTTP ^KyhhStg8

1. Filtro de entrada ^vfRdCM6R

7. Transferible ^k9dPzgja

Solicita ^1JgG1j7R

petición cruda ^HwXXghyD

petición filtrada ^KNAOPwJH

invoca lógica ^i8hdhvuI

solicita datos de DB ^rjrpdCi2

Query a DB ^X2XnyYRG

Retorna Query ^DKgOncaz

Mapeo respuesta de DB a Objeto ^7AsnruDt

solicita mapeo de Objeto a formato de Respuesta ^Z4Qb9hr0

OUTPUT ^Yx7mdHzq

Retorno de invocación lógica ^QadeSKhx

respuesta
petición ^V9G8azLh

Respuesta HTTP ^g1wZviLP

SERVIDOR REMOTO ^bf2NJrQt

 Server
Keycloak ^wVbs5V8g

Replica/redundancia parcial
(ID KC, roles, mapeo de niveles y
 organizaciones) ^YKo3WxQF

host:puerto/ruta ^HdyQQtrv

SERVIDORES
EXPUESTOS ^beHt1civ

CONTENEDORES CONFIGURADOS EN EL ARCHIVO COMPOSE.YAML ^KD6kN1Ak

1. Solicita Token ^9U9Pj73u

2. Retorna Token Auth ^mFsAbisu

3. Solicita Recurso c/ Token ^QkRNvdhX

Registrarse como cliente ^Z4gem8k6

 Clave pública ^gAUVm2Vb

Consulta DB ^HYTXkM1i

Respuesta DB ^7PLV7WfU

5. Query ^i8lNUQ05

6. Query Response ^HylW9HPK

7. HTTP Response ^YOE90dHS

proxy CUENTA DE SERVICIO ^xbJlngYE

admin ^6nXX38kA

update ^3iGGNQL6

USUARIOS ^OaEkdPNL

NGINX ^FhZ7oydd

USUARIOS ^hmiLuLto

ADMIN ^fbbe0boW

DB
Keycloak ^EOsYXtXg

DB
API ^4MhLGekD

API ^kWg6tfrk

API Proxy
Admin Keycloak ^G9q5XIva

ADMIN ^LTjPmgk4

Front-End ^T4Fe2VzW

4. Verifica token con clave pública de KC ^bJCdE2B2

FILESYSTEM ^KcmOzXZi

Leer y escribir ^ZYWrE98d

archivos recuperados ^SqL08ip9

📂 ^fmU84wfD

## Embedded Files
98b57af2ef779a2e2d078ae61dfceb78649ca235: [[Pasted Image 20250208192840_037.png]]

8513a797dadde271c9b8c11c76cd61b855ad6804: [[Pasted Image 20250208193048_919.png]]

9fb002ad064bc5e78a89da8908741b7ae8103b2b: [[Pasted Image 20250208195024_949.png]]

5f0d8993ce3d1f212bc188b426556e75cb6a4811: [[Pasted Image 20250208195322_307.png]]

a632f0998b2cd91b440735673d80faef701ebb40: [[docker-mark-blue.png]]

4d3ae3e0639ec06dcd5e5c01d96b362482130061: [[Pasted Image 20250208201227_902.png]]

7ee6ca07b02f2de90888eca4eb208dc78e3dc2fe: [[Pasted Image 20250208201930_998.png]]

1f1d16155f4ba486b48a69edd632249396305277: [[Pasted Image 20250209110727_185.png]]

e3a2ce77d47209bc393da8375f6c2ff6d5325d8f: [[Pasted Image 20250209112139_438.png]]

%%
## Drawing
```compressed-json
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
```
%%