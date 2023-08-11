import { Dispatch, HTMLAttributes, MouseEventHandler, SetStateAction } from 'react';
import { styled } from 'styled-components';
import { BodyKrMedium3 } from '../../styles/typefaces';
import { flexCenterCss } from '../../utils/commonStyle';
import RectButton from '../common/buttons/RectButton';
import { HYUNDAI_URL } from '../../utils/constants';
import { DimmedBackground } from './DimmedBackground';

interface ICloseModal extends HTMLAttributes<HTMLDivElement> {
  displayDimmed: boolean;
  setDisplayDimmed: Dispatch<SetStateAction<boolean>>;
}

export default function CloseModal({
  displayDimmed = false,
  setDisplayDimmed,
  ...props
}: ICloseModal) {
  const handleCloseClick = () => {
    location.href = HYUNDAI_URL;
  };
  const handleCancelCick = () => {
    setDisplayDimmed(false);
  };
  const stopEvent: MouseEventHandler<HTMLDivElement> = (e) => {
    e.stopPropagation();
  };

  return (
    <DimmedBackground $displayDimmed={displayDimmed} {...props}>
      <Modal onClick={stopEvent}>
        <Text>
          내 차 만들기를 종료하시겠습니까?
          <br />
          지금 서비스 종료 시 저장되지 않습니다.
        </Text>
        <ButtonWrapper>
          <Button active={false} type={'popup'} onClick={handleCancelCick}>
            취소
          </Button>
          <Button type={'popup'} onClick={handleCloseClick}>
            종료
          </Button>
        </ButtonWrapper>
      </Modal>
    </DimmedBackground>
  );
}

const Modal = styled.div`
  position: relative;
  display: flex;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  overflow: hidden;
  flex-direction: column;
  width: 335px;
  height: 200px;
  border-radius: 4px;
  background-color: ${({ theme }) => theme.color.white};
`;

const Text = styled.div`
  ${BodyKrMedium3}
  ${flexCenterCss}
  pointer-events: none;
  height: 100%;
  text-align: center;
`;

const ButtonWrapper = styled.div`
  display: flex;
  width: 100%;
`;

const Button = styled(RectButton)`
  width: 100%;
`;
