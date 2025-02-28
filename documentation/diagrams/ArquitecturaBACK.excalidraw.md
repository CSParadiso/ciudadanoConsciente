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
98b57af2ef779a2e2d078ae61dfceb78649ca235: [[user1PNG.png]]

8513a797dadde271c9b8c11c76cd61b855ad6804: [[user2PNG.png]]

9fb002ad064bc5e78a89da8908741b7ae8103b2b: [[postgresqlPNG.png]]

5f0d8993ce3d1f212bc188b426556e75cb6a4811: [[keycloakPNG.png]]

a632f0998b2cd91b440735673d80faef701ebb40: [[docker-mark-blue.png]]

4d3ae3e0639ec06dcd5e5c01d96b362482130061: [[quarkusPNG.png]]

7ee6ca07b02f2de90888eca4eb208dc78e3dc2fe: [[nginxPNG.png]]

1f1d16155f4ba486b48a69edd632249396305277: [[userAdminPNG.png]]

e3a2ce77d47209bc393da8375f6c2ff6d5325d8f: [[reactPNG.png]]

%%
## Drawing
```compressed-json
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
```
%%